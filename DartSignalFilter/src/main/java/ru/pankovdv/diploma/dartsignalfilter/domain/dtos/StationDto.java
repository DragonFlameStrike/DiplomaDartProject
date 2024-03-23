package ru.pankovdv.diploma.dartsignalfilter.domain.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StationDto {

    String station;

    Integer eventsCount;
}
