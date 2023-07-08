package ru.pankovdv.diploma.dartsignalfilter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.FileParser;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;

import java.util.List;

@Service
public class SignalUiService {

    private final String input = "signal.txt";

    @Autowired
    private FileParser parser;
    @Autowired
    private SignalService signalService;

    public ResultDto filter() {
        //Подготовка данных - преобразование взодных данных в массив точек
        List<Measurement> signal = parser.parse(input);
        ResultDto resultDto = signalService.filter(signal);
        return resultDto;
    }
}
