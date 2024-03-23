package ru.pankovdv.diploma.dartsignalfilter.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<StationEntity, Long> {
    StationEntity findByPathUrl(String line);
}

