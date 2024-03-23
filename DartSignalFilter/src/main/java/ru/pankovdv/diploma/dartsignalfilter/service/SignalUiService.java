package ru.pankovdv.diploma.dartsignalfilter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankovdv.diploma.dartsignalfilter.config.DSFConfig;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.FileParser;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.StringDataParser;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.EventDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.EventsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.StationDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.StationsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.EventEntity;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.EventRepository;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.StationEntity;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.StationRepository;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.HttpParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SignalUiService {

    private final String defaultData = "signal.txt";

    @Autowired
    private FileParser defaultParser;
    @Autowired
    private StringDataParser stringDataParser;
    @Autowired
    private SignalService signalService;
    @Autowired
    DSFConfig config;
    @Autowired
    HttpParser parser;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private EventRepository eventRepository;

    public ResultDto filter() {
        List<Measurement> signal;
        //Подготовка данных - преобразование входных данных в массив точек
        if (config.getCurrentEvent() != null) {
            var data = parser.parseWithFullUrl(config.getCurrentStation() ,config.getCurrentEvent());
            signal = stringDataParser.parse(data);
        } else {
            signal = defaultParser.parse(defaultData);
        }
        return signalService.filter(signal);
    }

    public StationsDtoResponse getStations() {
        List<StationDto> stationDtos = new ArrayList<>();
        var stations = stationRepository.findAll();

        for (StationEntity station : stations) {
            if (Objects.equals(station.getStationName(), "Station not found")) {
                continue;
            }
            var stationDto = StationDto.builder()
                    .id(station.getId())
                    .station(station.getStationName())
                    .eventsCount(station.getEvents().size())
                    .build();
            stationDtos.add(stationDto);
        }
        return StationsDtoResponse.builder().stations(stationDtos).build();
    }

    public EventsDtoResponse getEvents(Long currentStation) {
        List<EventDto> eventDtos = new ArrayList<>();
        var events = eventRepository.findAllByStationId(currentStation);

        for (EventEntity event: events) {
            var eventDto = EventDto.builder()
                    .id(event.getId())
                    .seriesTime(event.getSeriesTime())
                    .date(event.getDate())
                    .build();
            eventDtos.add(eventDto);
        }
        return EventsDtoResponse.builder().events(eventDtos).build();
    }
}
