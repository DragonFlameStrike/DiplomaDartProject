package ru.pankovdv.diploma.dartsignalfilter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pankovdv.diploma.dartsignalfilter.config.DataConfig;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.httpParser;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventDto;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventsDto;
import ru.pankovdv.diploma.dartsignalfilter.service.SignalUiService;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    @Autowired
    SignalUiService signalUiService;

    @Autowired
    httpParser parser;

    @Autowired
    DataConfig dataConfig;

    @PostMapping("/filter")
    public void filterSignal(@RequestBody EventDto event) {
        String data = parser.parseWithFullUrl(event.getSeriestime());
        dataConfig.setData(data);
        dataConfig.setDataExist(true);
    }

    @GetMapping("/filter")
    public @ResponseBody ResultDto getFilteredSignal() {
        ResultDto resultDto = signalUiService.filter();
        return resultDto;
    }

    @GetMapping("/parse")
    public @ResponseBody EventsDto parseDataFromHttp() {
        return parser.parseAllEvents();
    }

//    @GetMapping("/filter/csv")
//    public @ResponseBody String getFilteredSignalCSV() {
//
//    }
}
