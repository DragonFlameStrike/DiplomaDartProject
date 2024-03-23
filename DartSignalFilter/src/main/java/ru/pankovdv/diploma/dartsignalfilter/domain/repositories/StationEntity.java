package ru.pankovdv.diploma.dartsignalfilter.domain.repositories;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "stations")
@Data
public class StationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path_url")
    private String pathUrl;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "ready_to_use")
    private Boolean readyToUse;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL)
    private List<EventEntity> events;
}

