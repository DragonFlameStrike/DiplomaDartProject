package ru.pankovdv.diploma.dartsignalfilter.domain.dtos;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EventDto {

    Long id;

    String seriesTime;

    String date;
}
