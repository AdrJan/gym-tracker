package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Exercise;
import com.adrjan.gymtracker.model.ExerciseForm;
import com.adrjan.gymtracker.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @GetMapping
    public String showExercise(Model model) {
        model.addAttribute("exercises", exerciseService.getAllExercises());
        if (!model.containsAttribute("exerciseForm"))
            model.addAttribute("exerciseForm", new ExerciseForm());

        return "exercise";
    }

    @GetMapping("/getExercises")
    public @ResponseBody List<Exercise> getExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/getExerciseVolume")
    public @ResponseBody Map<String, Integer> getExerciseVolume(@RequestParam int exerciseId, @RequestParam int limit) {
        return exerciseService.getLastExerciseVolumes(exerciseId, limit);
    }

    @PostMapping
    public String addExercise(@ModelAttribute @Valid ExerciseForm exerciseForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exerciseForm", bindingResult);
            redirectAttributes.addFlashAttribute("exerciseForm", exerciseForm);
        } else {
            exerciseService.addExercise(exerciseForm);
        }
        return "redirect:/exercise";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteExercise(@PathVariable int id) {
        exerciseService.deleteExercise(id);
        return "redirect:/exercise";
    }
}
