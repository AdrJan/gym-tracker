package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.model.MeasureForm;
import com.adrjan.gymtracker.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
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
                .build();

        measurementRepository.save(measurement);

        return "redirect:/progress";
    }

    @GetMapping("/getMeasurements")
    public @ResponseBody List<Measurement> getMeasurements(@RequestParam(defaultValue = "10") long limit) {
        return StreamSupport.stream(measurementRepository.findAll().spliterator(), false)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
