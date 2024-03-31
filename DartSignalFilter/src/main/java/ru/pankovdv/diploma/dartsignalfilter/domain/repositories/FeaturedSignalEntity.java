package ru.pankovdv.diploma.dartsignalfilter.domain.repositories;

import jakarta.persistence.*;
import lombok.Data;
import ru.pankovdv.diploma.dartsignalfilter.domain.Segment;
import ru.pankovdv.diploma.dartsignalfilter.domain.SegmentConverter;

@Entity
@Table(name = "featured")
@Data
public class FeaturedSignalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series_time")
    private String seriesTime;

    @Column(name = "station_name")
    private String stationName;

    @Convert(converter = SegmentConverter.class)
    @Column(name = "main_signal")
    private Segment mainSignal;

    @Convert(converter = SegmentConverter.class)
    @Column(name = "filtered_signal")
    private Segment filteredSignal;
}
