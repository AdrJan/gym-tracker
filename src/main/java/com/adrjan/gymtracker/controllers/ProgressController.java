package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.model.ExerciseForm;
import com.adrjan.gymtracker.model.MeasureForm;
import com.adrjan.gymtracker.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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
}
