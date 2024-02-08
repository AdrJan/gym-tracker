package com.adrjan.gymtracker.repositories;

import com.adrjan.gymtracker.entity.Measurement;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {

    List<Measurement> findByCreatedAt(LocalDate date);
}
