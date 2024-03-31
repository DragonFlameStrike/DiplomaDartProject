package ru.pankovdv.diploma.dartsignalfilter.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturedSignalRepository extends JpaRepository<FeaturedSignalEntity,Long> {
}
