package com.adrjan.gymtracker.repositories;

import com.adrjan.gymtracker.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findByCreatedAt(LocalDate date);
}
