package ru.pankovdv.diploma.dartsignalfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pankovdv.diploma.dartsignalfilter.config.DSFConfig;
import ru.pankovdv.diploma.dartsignalfilter.config.DataConfig;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.EventsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.StationsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.HttpParser;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.domain.EventDto;
import ru.pankovdv.diploma.dartsignalfilter.service.SignalUiService;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    @Autowired
    SignalUiService signalUiService;

    @Autowired
    HttpParser parser;

    @Autowired
    DataConfig dataConfig;

    @Autowired
    DSFConfig config;

    @GetMapping("/filter")
    public @ResponseBody
    ResultDto getFilteredSignal() {
        return signalUiService.filter();
    }

    @GetMapping("/stations")
    public @ResponseBody
    StationsDtoResponse getStations() {
        return signalUiService.getStations();
    }

    @GetMapping("/parseAll")
    public void parseStations() {
        parser.parseAllStations();
    }

    @GetMapping("/stations/set-current-station/{id}")
    public void setCurrentStation(@PathVariable Long id) {
        config.setCurrentStation(id);
    }

    @GetMapping("/stations/set-current-event/{id}")
    public void setCurrentEvent(@PathVariable Long id) {
        config.setCurrentEvent(id);
    }

    @GetMapping("/stations/get-config")
    public @ResponseBody
    DSFConfig getConfig() {
        return config;
    }

    @GetMapping("/events")
    public @ResponseBody
    EventsDtoResponse getEvents() {
        return signalUiService.getEvents(config.getCurrentStation());
    }

//    @GetMapping("/filter/csv")
//    public @ResponseBody String getFilteredSignalCSV() {
//
//    }
}
