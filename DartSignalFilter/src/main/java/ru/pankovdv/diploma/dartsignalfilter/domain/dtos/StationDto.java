package ru.pankovdv.diploma.dartsignalfilter.domain.dtos;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StationDto {

    Long id;

    String station;

    Integer eventsCount;
}
