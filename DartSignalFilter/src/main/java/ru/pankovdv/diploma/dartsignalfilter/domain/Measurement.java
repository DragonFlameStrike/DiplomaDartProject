package ru.pankovdv.diploma.dartsignalfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    private Double time;
    private Double height;
    private Integer type;
}
