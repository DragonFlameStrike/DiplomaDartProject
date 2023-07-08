package ru.pankovdv.diploma.dartsignalfilter.dataPreparator;

import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;

public interface Interpolator {
    Segment interpolateSegment(Segment segment);
}
