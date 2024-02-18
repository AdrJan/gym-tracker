package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Measurement;
import com.adrjan.gymtracker.model.MeasureForm;
import com.adrjan.gymtracker.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Autowired
    ProgressService progressService;

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

        ProgressService.PostedMeasurementResult postedMeasurementResult = progressService.postMeasurement(measureForm);
        boolean postSuccess = postedMeasurementResult.getSuccess();
        Measurement postMeasurement = postedMeasurementResult.getMeasurement();

        if (!postSuccess && Objects.isNull(postMeasurement))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "W bazie znajduje się kilka wyników pomiarów dotyczących tego samego dnia.");

        if (!postSuccess) {
            redirectAttributes.addFlashAttribute("foundMeasurement", postMeasurement);
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
        return progressService.getMeasurements(page, size, sortBy, sortOrder);
    }

    @GetMapping("/measurement")
    public @ResponseBody Measurement getMeasurement(@RequestParam int id) {
        return progressService.getMeasurement(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pomiar nie został znaleziony"));
    }

    @DeleteMapping("/deleteMeasurement/{id}")
    public ResponseEntity<String> deleteMeasurement(@PathVariable int id) {
        if (progressService.deleteMeasurement(id))
            return ResponseEntity.ok("Pomiar został poprawnie usnięty");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Pomiar nie mógl być poprawnie usunięty.");
    }

    @PutMapping("/updateMeasurement/{id}")
    public ResponseEntity<String> updateMeasurement(@PathVariable int id, @RequestBody Measurement updatedMeasurement) {
        if (progressService.updateMeasurement(id, updatedMeasurement))
            return ResponseEntity.ok("Udało się prawidłowo zaktualizować dane.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Wystąpił błąd podczas aktualizacji pomiaru");
    }
}
