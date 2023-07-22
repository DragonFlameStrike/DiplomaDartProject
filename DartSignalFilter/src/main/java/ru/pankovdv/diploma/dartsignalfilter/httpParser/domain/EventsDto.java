package ru.pankovdv.diploma.dartsignalfilter.httpParser.domain;

import lombok.Data;

import java.util.List;

@Data
public class EventsDto {
    List<EventDto> events;
}
