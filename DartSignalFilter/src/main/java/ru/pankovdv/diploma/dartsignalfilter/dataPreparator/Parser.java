package ru.pankovdv.diploma.dartsignalfilter.dataPreparator;

import ru.pankovdv.diploma.dartsignalfilter.domain.Measurement;

import java.util.List;

public interface Parser {
    List<Measurement> parse(String filename);
}
