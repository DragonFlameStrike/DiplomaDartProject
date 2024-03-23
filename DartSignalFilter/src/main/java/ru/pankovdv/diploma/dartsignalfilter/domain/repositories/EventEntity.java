package ru.pankovdv.diploma.dartsignalfilter.domain.repositories;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "series_time")
    private String seriesTime;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;
}

