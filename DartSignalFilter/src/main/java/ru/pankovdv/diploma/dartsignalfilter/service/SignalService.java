package ru.pankovdv.diploma.dartsignalfilter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankovdv.diploma.dartsignalfilter.config.DSFConfig;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.Cleaner;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.LinearInterpolator;
import ru.pankovdv.diploma.dartsignalfilter.dataPreparator.MeasurementSplitter;
import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;
import ru.pankovdv.diploma.dartsignalfilter.domain.ResultDto;
import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;
import ru.pankovdv.diploma.dartsignalfilter.filter.CustomFilter;
import ru.pankovdv.diploma.dartsignalfilter.filter.FFT;
import ru.pankovdv.diploma.dartsignalfilter.filter.SegmentsSubtractor;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignalService {
    @Autowired
    private Cleaner cleaner;
    @Autowired
    private FFT fft;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private MeasurementSplitter measurementSplitter;
    @Autowired
    private LinearInterpolator linearInterpolator;
    @Autowired
    private SegmentsSubtractor segmentsSubtractor;
    @Autowired
    DSFConfig config;

    public ResultDto filter(List<Measurement> signal) {
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory(); // Используемая память до выполнения функции


        //Удаляем ошибочные данные
        cleaner.clearSignal(signal);
        //Режем весь сигнал на сегменты включающие точки с типом 2 и 3
        List<Segment> segments = measurementSplitter.split(signal);
        // Удаляем  сегменты незначительного размера
        cleaner.clearSegments(segments);
        // Добавляем доп точки в сегменты для однородности сигнала
        List<Segment> intrSegments = new ArrayList<>();
        for (Segment segment: segments) {
            var interpolateSegment = linearInterpolator.interpolateSegment(segment);
            var trimSegment = cleaner.trimSegment(interpolateSegment);
            intrSegments.add(trimSegment);
        }

        //Фильтруем
        List<Segment> filteredSegments = fft.filter(intrSegments, config.getThresholdFrequency());

        for (int i = 0;i< config.getAproxiTimes(); i++) {
            filteredSegments = customFilter.filter(filteredSegments);
        }

        //Вычитаем из основного сигнала, содержащего обе компоненты, сигнал волнения океана
        List<Segment> tsunamiSegments = segmentsSubtractor.subtract(intrSegments,filteredSegments);

        //Переформировываем основной сигнал для удобства
        List<Segment> mainSignal = new ArrayList<>();
        Segment mainSegment = new Segment();
        mainSegment.addAllMeasurements(signal);
        mainSignal.add(mainSegment);

        long endTime = System.nanoTime();
        double durationInSeconds = (double)(endTime - startTime) / 1_000_000_000.0;
        runtime.gc();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory(); // Используемая память после выполнения функции
        double spentMemoryInMB = (double)(memoryAfter - memoryBefore)/(1024*1024);

        return new ResultDto(mainSignal,intrSegments,filteredSegments,tsunamiSegments,durationInSeconds,spentMemoryInMB);

    }
}
