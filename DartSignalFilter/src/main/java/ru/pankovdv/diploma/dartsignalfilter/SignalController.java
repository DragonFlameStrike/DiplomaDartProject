package ru.pankovdv.diploma.dartsignalfilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pankovdv.diploma.dartsignalfilter.config.DSFConfig;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.EventsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.FeaturedDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.SaveSignalDtoRequest;
import ru.pankovdv.diploma.dartsignalfilter.domain.dtos.StationsDtoResponse;
import ru.pankovdv.diploma.dartsignalfilter.httpParser.HttpParser;
import ru.pankovdv.diploma.dartsignalfilter.service.SignalUiService;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    @Autowired
    SignalUiService signalUiService;

    @Autowired
    HttpParser parser;

    @Autowired
    DSFConfig config;

    @GetMapping("/filter")
    public @ResponseBody
    ResultDto getFilteredSignal() {
        return signalUiService.filter();
    }

    @PostMapping("/save")
    public void saveSignal(@RequestBody SaveSignalDtoRequest signalRequest) {
        signalUiService.saveSignal(signalRequest);
    }

    @GetMapping("/featured")
    public @ResponseBody FeaturedDtoResponse getFeatured() {
        return signalUiService.getFeatured();
    }

    @GetMapping("/stations")
    public @ResponseBody
    StationsDtoResponse getStations() {
        return signalUiService.getStations();
    }

    @GetMapping("/events")
    public @ResponseBody
    EventsDtoResponse getEvents() {
        return signalUiService.getEvents(config.getCurrentStation());
    }

    @GetMapping("/config/set-current-station/{id}")
    public void setCurrentStation(@PathVariable Long id) {
        String stationName = signalUiService.getStationName(id);
        config.setCurrentStationName(stationName);
        config.setCurrentStation(id);
    }

    @GetMapping("/config/set-current-event/{id}")
    public void setCurrentEvent(@PathVariable Long id) {
        String eventDate = signalUiService.getEventDate(id);
        config.setCurrentEventDate(eventDate);
        config.setCurrentEvent(id);
    }

    @GetMapping("/config/set-threshold-frequency/{thresholdFrequency}")
    public void setThresholdFrequency(@PathVariable Double thresholdFrequency) {
        config.setThresholdFrequency(thresholdFrequency);
    }

    @GetMapping("/config/set-aproxy-times/{aproxyTimes}")
    public void setAproxyTimes(@PathVariable Integer aproxyTimes) {
        config.setAproxiTimes(aproxyTimes);
    }

    @GetMapping("/config/get-config")
    public @ResponseBody
    DSFConfig getConfig() {
        return config;
    }

    @GetMapping("/settings/update-stations")
    public void parseStations() {
        parser.parseAllStations();
    }
}
