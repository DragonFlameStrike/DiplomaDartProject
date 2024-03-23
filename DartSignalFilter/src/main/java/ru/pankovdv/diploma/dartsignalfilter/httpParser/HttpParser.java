package ru.pankovdv.diploma.dartsignalfilter.httpParser;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.EventEntity;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.EventRepository;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.StationEntity;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.StationRepository;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventDto;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventsDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class HttpParser {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private EventRepository eventRepository;

    private String startyear = "0000";
    private String startmonth = "00";
    private String startday = "00";
    private String endyear = "0000";
    private String endmonth = "00";
    private String endday = "00";


    public String parseWithFullUrl(Long stationId, Long eventId) {
        var station = stationRepository.findById(stationId).orElseThrow();
        var event = eventRepository.findById(eventId).orElseThrow();

        // URL для GET запроса
        String url = station.getPathUrl() +
                "&type=2" +
                "&seriestime=" + event.getSeriesTime() +
                "&startyear=" + startyear +
                "&startmonth=" + startmonth +
                "&startday=" + startday +
                "&endyear=" + endyear +
                "&endmonth=" + endmonth +
                "&endday=" + endday +
                "&submit=Submit";

        try {
            // Выполняем GET запрос и получаем содержимое страницы
            String pageContent = sendGetRequest(url);

            // Парсим HTML с помощью Jsoup
            Document document = Jsoup.parse(pageContent);

            // Извлекаем данные из указанного элемента
            return document.select("textarea#data").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void parseAllStations() {
        log.info("Парсинг всех станций: старт.");
        try (BufferedReader reader = new BufferedReader(new FileReader("stationlist.txt"))) {
            String stationUrl;
            Integer countLines = 0;
            while ((stationUrl = reader.readLine()) != null) {
                if (stationRepository.findByPathUrl(stationUrl) == null) {
                    countLines++;
                    String stationName = getStationName(stationUrl);
                    var station = new StationEntity();
                    station.setStationName(stationName);
                    station.setPathUrl(stationUrl);
                    station.setReadyToUse(false);
                    stationRepository.save(station);
                    log.info("Парсинг всех станций: Добавлена новая станция - {}.", stationName);
                } else {
                    log.info("Парсинг всех станций: Станция уже существует - {}.", stationUrl);
                }
            }
            log.info("Парсинг всех станций: конец. Всего записей добавлено - {}.", countLines);

            var stations = stationRepository.findAll();
            log.info("Парсинг ивентов для всех станций: старт.");
            for (StationEntity station : stations) {
                if (!station.getReadyToUse()) {
                    parseAllEvents(station.getPathUrl(), station);
                } else {
                    log.info("Парсинг ивентов для всех станций: станция {} уже обработана.", station);
                }
            }
            log.info("Парсинг ивентов для всех станций: конец.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStationName(String stationUrl) {
        try {
            // Выполняем GET запрос и получаем содержимое страницы
            String pageContent = sendGetRequest(stationUrl);

            // Парсим HTML с помощью Jsoup
            Document document = Jsoup.parse(pageContent);

            // Извлекаем элемент <h1> с названием станции
            Element h1Element = document.select("h1").first();

            // Получаем текст из элемента
            String stationName = h1Element.text();

            // Возвращаем название станции
            return stationName;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Название отсутвует";
    }

    private void parseAllEvents(String stationUrl, StationEntity station) {
        log.info("Парсинг ивентов для станции {}: старт.", stationUrl);
        try {
            // Выполняем GET запрос и получаем содержимое страницы
            String pageContent = sendGetRequest(stationUrl);

            // Парсим HTML с помощью Jsoup
            Document document = Jsoup.parse(pageContent);


            // Извлекаем содержимое JavaScript секции
            String scriptContent = document.select("script[type=text/javascript]").html();

            // Ищем значение переменной event_date_array с помощью регулярного выражения
            List<String> eventDateArrayValue = extractEventDateArrayValues(scriptContent);

            EventsDto events = eventDateArrayValueToDto(eventDateArrayValue);

            for (EventDto eventDto: events.getEvents()) {
                EventEntity eventEntity = new EventEntity();
                eventEntity.setDate(eventDto.getDate());
                eventEntity.setSeriesTime(eventDto.getSeriestime());
                eventEntity.setStation(station);
                eventRepository.save(eventEntity);

                log.info("Парсинг ивентов для станции {}: сохранен новый ивент - {}.", stationUrl, eventEntity.getSeriesTime());
                station.setReadyToUse(true);
                stationRepository.save(station);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Парсинг ивентов для станции {}: конец.", stationUrl);
    }

    // Метод для отправки GET запроса и получения содержимого страницы
    private String sendGetRequest(String url) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity);
    }

    // Метод для извлечения всех значений переменной event_date_array с помощью регулярного выражения
    private List<String> extractEventDateArrayValues(String scriptContent) {
        List<String> eventDateArrayValues = new ArrayList<>();
        Pattern pattern = Pattern.compile("\n\t\t\"(.*?)\",");
        Matcher matcher = pattern.matcher(scriptContent);

        while (matcher.find()) {
            String eventDateArrayContent = matcher.group(1);
            eventDateArrayValues.add(eventDateArrayContent);
        }

        return eventDateArrayValues;
    }

    private EventsDto eventDateArrayValueToDto(List<String> events) {
        EventsDto eventsDto = new EventsDto();
        List<EventDto> eventDtoList = new ArrayList<>();
        for (String event : events) {
            eventDtoList.add(convertStringToEventDto(event));
        }
        eventsDto.setEvents(eventDtoList);
        return eventsDto;
    }

    public static EventDto convertStringToEventDto(String input) {
        EventDto eventDto = new EventDto();
        String[] parts = input.split(";");

        eventDto.setSeriestime(parts[0]);
        eventDto.setDate(parts[1]);

        return eventDto;
    }
}
