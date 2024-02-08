package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.model.MeasureForm;
import com.adrjan.gymtracker.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<Integer, String> measurementMap = new HashMap<>();
        getMeasurements(10).forEach(x -> measurementMap.put(
                x.getId(),
                x.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        model.addAttribute("measurementMap", measurementMap);
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
        if(measurementRepository.findByCreatedAt(todayDate).isEmpty()) {
            measurementRepository.save(measurement);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Dzisiejszego dnia już dokonałeś pomiaru." +
                            " Jeśli chcesz, możesz go edytować na liście obok.");
        }

        return "redirect:/progress";
    }

    @GetMapping("/getMeasurements")
    public @ResponseBody List<Measurement> getMeasurements(@RequestParam(defaultValue = "10") long limit) {
        return StreamSupport.stream(measurementRepository.findAll().spliterator(), false)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @GetMapping("/getMeasurement")
    public @ResponseBody Measurement getMeasurement(@RequestParam int id) {
        Optional<Measurement> measurement = measurementRepository.findById(id);

        return measurement.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Measurement not found with id: " + id));
    }
}
