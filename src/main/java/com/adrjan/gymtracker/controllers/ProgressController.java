package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.model.MeasureForm;
import com.adrjan.gymtracker.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    MeasurementRepository measurementRepository;

    @GetMapping
    public String showProgressPage(Model model) {
        if (!model.containsAttribute("measureForm"))
            model.addAttribute("measureForm", new MeasureForm());

        List<Measurement> measurements = getMeasurements(0, 10, "id", "desc");

        model.addAttribute("measurements", measurements);

        return "progress";
    }

    @PostMapping
    public String postMeasurement(@ModelAttribute @Valid MeasureForm measureForm,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.measureForm", bindingResult);
            redirectAttributes.addFlashAttribute("measureForm", measureForm);
            return "redirect:/progress";
        }

        Measurement measurement = Measurement.builder()
                .leftBiceps(measureForm.getLeftBiceps())
                .rightBiceps(measureForm.getRightBiceps())
                .chest(measureForm.getChest())
                .waist(measureForm.getWaist())
                .leftThigh(measureForm.getLeftThigh())
                .rightThigh(measureForm.getRightThigh())
                .weight(measureForm.getWeight())
                .build();

        LocalDate todayDate = LocalDate.now();
        // Powinno zwrócić jedno.
        List<Measurement> measurements = measurementRepository.findByCreatedAt(todayDate);
        if (measurements.isEmpty()) {
            measurementRepository.save(measurement);
        } else if (measurements.size() > 1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "W bazie znajduje się kilka wyników pomiarów dotyczących tego samego dnia.");
        } else {
            redirectAttributes.addFlashAttribute("foundMeasurement", measurements.get(0));
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Dzisiejszego dnia już dokonałeś pomiaru." +
                            " Jeśli chcesz, możesz go edytować na liście obok.");
        }

        return "redirect:/progress";
    }

    @GetMapping("/measurements")
    public @ResponseBody List<Measurement> getMeasurements(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(required = false, defaultValue = "id") String sortBy,
                                                           @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return StreamSupport.stream(measurementRepository
                        .findAll(PageRequest.of(page, size, Sort.by(direction, sortBy)))
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/measurement")
    public @ResponseBody Measurement getMeasurement(@RequestParam int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);

        return measurement.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Measurement not found with id: " + id));
    }

    @DeleteMapping("/deleteMeasurement/{id}")
    public ResponseEntity<?> deleteMeasurement(@PathVariable int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);
        if (measurement.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            measurementRepository.delete(measurement.get());
            return ResponseEntity.ok().build();
        }
    }
}
