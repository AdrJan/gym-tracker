package com.adrjan.gymtracker.service;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.model.MeasureForm;
import com.adrjan.gymtracker.repositories.MeasurementRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProgressService {

    @Autowired
    MeasurementRepository measurementRepository;

    public boolean updateMeasurement(int id, Measurement updatedMeasurement) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);

        if (optionalMeasurement.isEmpty())
            return false;

        Measurement measurement = optionalMeasurement.get();

        if (updatedMeasurement.getLeftBiceps() != 0)
            measurement.setLeftBiceps(updatedMeasurement.getLeftBiceps());
        if (updatedMeasurement.getRightBiceps() != 0)
            measurement.setRightBiceps(updatedMeasurement.getRightBiceps());
        if (updatedMeasurement.getLeftThigh() != 0)
            measurement.setLeftThigh(updatedMeasurement.getLeftThigh());
        if (updatedMeasurement.getRightThigh() != 0)
            measurement.setRightThigh(updatedMeasurement.getRightThigh());
        if (updatedMeasurement.getWeight() != 0)
            measurement.setWeight(updatedMeasurement.getWeight());
        if (updatedMeasurement.getWaist() != 0)
            measurement.setWaist(updatedMeasurement.getWaist());
        if (updatedMeasurement.getChest() != 0)
            measurement.setLeftBiceps(updatedMeasurement.getChest());

        measurementRepository.save(measurement);

        return true;
    }

    public Optional<Measurement> getMeasurement(int id) {
        return measurementRepository.findById(id);
    }

    public List<Measurement> getMeasurements(int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return StreamSupport.stream(measurementRepository
                        .findAll(PageRequest.of(page, size, Sort.by(direction, sortBy)))
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

    public PostedMeasurementResult postMeasurement(MeasureForm measureForm) {
        LocalDate todayDate = LocalDate.now();

        List<Measurement> todayMeasurements = measurementRepository.findByCreatedAt(todayDate);

        if (todayMeasurements.size() > 1)
            return PostedMeasurementResult.builder()
                    .success(Boolean.FALSE)
                    .measurement(null)
                    .build();

        if (!todayMeasurements.isEmpty())
            return PostedMeasurementResult.builder()
                    .success(Boolean.FALSE)
                    .measurement(todayMeasurements.get(0))
                    .build();

        Measurement newMeasurement = Measurement.builder()
                .leftBiceps(measureForm.getLeftBiceps())
                .rightBiceps(measureForm.getRightBiceps())
                .chest(measureForm.getChest())
                .waist(measureForm.getWaist())
                .leftThigh(measureForm.getLeftThigh())
                .rightThigh(measureForm.getRightThigh())
                .weight(measureForm.getWeight())
                .build();

        measurementRepository.save(newMeasurement);

        return PostedMeasurementResult.builder()
                .success(Boolean.TRUE)
                .measurement(measurementRepository.save(newMeasurement))
                .build();
    }

    public boolean deleteMeasurement(int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);

        if (measurement.isEmpty())
            return false;

        measurementRepository.delete(measurement.get());

        return true;
    }

    @Getter
    @Setter
    @Builder
    public static class PostedMeasurementResult {
        private Boolean success;
        private Measurement measurement;
    }
}
