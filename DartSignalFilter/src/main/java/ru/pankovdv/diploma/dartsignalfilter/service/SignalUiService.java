package ru.pankovdv.diploma.dartsignalfilter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankovdv.diploma.dartsignalfilter.config.DataConfig;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.FileParser;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.StringDataParser;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;

import java.util.List;

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

    public ResultDto filter() {
        List<Measurement> signal;
        //Подготовка данных - преобразование взодных данных в массив точек
        if(dataConfig.isDataExist()){
            signal = stringDataParser.parse(dataConfig.getData());
        } else {
            signal = defaultParser.parse(defaultData);
        }
        ResultDto resultDto = signalService.filter(signal);
        return resultDto;
    }
}
