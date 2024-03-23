package ru.pankovdv.diploma.dartsignalfilter.domain.dtos;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class StationsDtoResponse {

    List<StationDto> stations;
}
