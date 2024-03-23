package ru.pankovdv.diploma.dartsignalfilter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankovdv.diploma.dartsignalfilter.config.DataConfig;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.FileParser;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.StringDataParser;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.StationDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.StationsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.StationEntity;
import ru.pankovdv.diploma.dartsignalfilter.domain.repositories.StationRepository;

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
    private DataConfig dataConfig;

    @Autowired
    private StationRepository stationRepository;

    public ResultDto filter() {
        List<Measurement> signal;
        //Подготовка данных - преобразование входных данных в массив точек
        if (dataConfig.isDataExist()) {
            signal = stringDataParser.parse(dataConfig.getData());
        } else {
            signal = defaultParser.parse(defaultData);
        }
        ResultDto resultDto = signalService.filter(signal);
        return resultDto;
    }

    public StationsDtoResponse getStations() {
        List<StationDto> stationDtos = new ArrayList<>();
        var stations = stationRepository.findAll();

        for (StationEntity station : stations) {
            if (Objects.equals(station.getStationName(), "Station not found")) {
                continue;
            }
            var stationDto = StationDto.builder()
                    .station(station.getStationName())
                    .eventsCount(station.getEvents().size())
                    .build();
            stationDtos.add(stationDto);
        }
        return StationsDtoResponse.builder().stations(stationDtos).build();
    }
}
