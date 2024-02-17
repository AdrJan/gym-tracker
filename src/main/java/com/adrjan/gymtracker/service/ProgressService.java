package com.adrjan.gymtracker.service;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private MeasurementRepository measurementRepository;

    public boolean updateMeasurement(Integer id, Measurement updatedMeasurement) {
        Optional<Measurement> measurement = measurementRepository.findById(id);
        if (measurement.isEmpty()) {
            return false;
        } else {
            System.out.println(updatedMeasurement.toString());
            return true;
        }
    }
}
