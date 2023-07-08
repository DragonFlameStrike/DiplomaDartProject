package ru.pankovdv.diploma.dartsignalfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResultDto {
    List<Segment> mainSignal;
    List<Segment> extrapolatedSignal;
    List<Segment> filteredSignal;
    List<Segment> tsunamiSignal;
    Double spentTime;
    Double spentMemory;
}
