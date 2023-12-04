package com.adrjan.gymtracker.repositories;

import com.adrjan.gymtracker.entity.Measurement;
import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {
}
