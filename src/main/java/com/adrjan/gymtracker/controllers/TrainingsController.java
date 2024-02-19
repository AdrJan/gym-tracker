package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.TrainingSession;
import com.adrjan.gymtracker.service.TrainingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/trainings")
public class TrainingsController {


    @Autowired
    private TrainingsService trainingsService;

    @GetMapping
    public String showPage(Model model) {
        baseShowPage(model);
        return "trainings";
    }

    @GetMapping("/{id}")
    public String showPage(@PathVariable("id") int id, Model model) {
        baseShowPage(model);

        model.addAttribute("trainingSession", trainingsService
                .getTrainingSessionById(id)
                .orElseGet(TrainingSession::new));

        return "trainings";
    }

    @PostMapping
    public String selectTraining(@ModelAttribute TrainingSession selectedTraining, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("training", selectedTraining);
        return "redirect:/trainings";
    }

    private void baseShowPage(Model model) {
        model.addAttribute("trainings", trainingsService.getTrainingSessions());
    }
}
