package ru.pankovdv.diploma.dartsignalfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Segment {
    private List<Measurement> measurements;
    private Integer firstIndex;
    private Integer lastIndex;

    public Segment() {
        measurements = new ArrayList<>();
    }

    public void addMeasurement(Measurement measurement){
        this.measurements.add(measurement);
    }

    public void addAllMeasurements(List<Measurement> measurements){
        this.measurements.addAll(measurements);
    }

}
