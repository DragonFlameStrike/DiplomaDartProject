package ru.pankovdv.diploma.dartsignalfilter.domain.dtos;

import lombok.Builder;
import lombok.Value;
import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;

@Value
@Builder
public class SaveSignalDtoRequest {

    Segment extrapolatedSignal;
    Segment tsunamiSignal;
    String stationName;
    String eventDate;
}

