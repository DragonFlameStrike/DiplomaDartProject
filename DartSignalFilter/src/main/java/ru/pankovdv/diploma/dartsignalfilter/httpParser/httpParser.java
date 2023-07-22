package ru.pankovdv.diploma.dartsignalfilter.httpParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventDto;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventsDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class httpParser {

    private String startyear = "0000";
    private String startmonth = "00";
    private String startday = "00";
    private String endyear = "0000";
    private String endmonth = "00";
    private String endday = "00";


    public String parseWithFullUrl(String seriestime){
        // URL для GET запроса
        String url = "https://www.ndbc.noaa.gov/station_page.php?station=56003" +
                "&type=2" +
                "&seriestime="+seriestime+
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
            String data = document.select("textarea#data")
                    .text();

            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public EventsDto parseAllEvents() {
        String url = "https://www.ndbc.noaa.gov/station_page.php?station=56003";
        try {
            // Выполняем GET запрос и получаем содержимое страницы
            String pageContent = sendGetRequest(url);

            // Парсим HTML с помощью Jsoup
            Document document = Jsoup.parse(pageContent);


            // Извлекаем содержимое JavaScript секции
            String scriptContent = document.select("script[type=text/javascript]").html();

            // Ищем значение переменной event_date_array с помощью регулярного выражения
            List<String> eventDateArrayValue = extractEventDateArrayValues(scriptContent);


            // Выводим полученные данные
            return eventDateArrayValueToDto(eventDateArrayValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new EventsDto();
    }

    // Метод для отправки GET запроса и получения содержимого страницы
    private  String sendGetRequest(String url) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity);
    }

    // Метод для извлечения всех значений переменной event_date_array с помощью регулярного выражения
    private  List<String> extractEventDateArrayValues(String scriptContent) {
        List<String> eventDateArrayValues = new ArrayList<>();
        Pattern pattern = Pattern.compile("\n\t\t\"(.*?)\",");
        Matcher matcher = pattern.matcher(scriptContent);

        while (matcher.find()) {
            String eventDateArrayContent = matcher.group(1);
            eventDateArrayValues.add(eventDateArrayContent);
        }

        return eventDateArrayValues;
    }

    private EventsDto eventDateArrayValueToDto(List<String> events){
        EventsDto eventsDto = new EventsDto();
        List<EventDto> eventDtoList = new ArrayList<>();
        for (String event: events) {
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
