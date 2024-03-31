package ru.pankovdv.diploma.dartsignalfilter.domain.dtos;

import lombok.Builder;
import lombok.Value;
import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;

@Value
@Builder
public class FeaturedDtoResponse {
    String firstStationName;
    String firstEventDate;
    String secondStationName;
    String secondEventDate;
    Segment firstMainSignal;
    Segment firstFilteredSignal;
    Segment secondMainSignal;
    Segment secondFilteredSignal;
}
