package ru.pankovdv.diploma.dartsignalfilter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.service.SignalUiService;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    @Autowired
    SignalUiService signalUiService;
//    @PostMapping("/filter")
//    public ResultDto filterSignal(@RequestBody FilterRequestDto requestDto) {
//        // Здесь вы можете добавить логику фильтрации сигналов на основе переданных данных
//        // и создать объект ResultDto с информацией о отфильтрованном сигнале
//        ResultDto resultDto = new ResultDto(/* информация о фильтрованном сигнале */);
//        return resultDto;
//    }

    @GetMapping("/filter")
    public @ResponseBody ResultDto getFilteredSignal() {
        ResultDto resultDto = signalUiService.filter();
        return resultDto;
    }

//    @GetMapping("/filter/csv")
//    public @ResponseBody String getFilteredSignalCSV() {
//
//    }
}
