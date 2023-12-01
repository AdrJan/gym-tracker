package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Exercise;
import com.adrjan.gymtracker.entity.ExerciseSession;
import com.adrjan.gymtracker.repositories.ExerciseRepository;
import com.adrjan.gymtracker.repositories.ExerciseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseSessionRepository exerciseSessionRepository;

    @GetMapping
    public String showExercise(Model model) {
        List<Exercise> exercises = new ArrayList<>();
        exerciseRepository.findAll().forEach(exercises::add);

        model.addAttribute("exercises", exercises);
        if (!model.containsAttribute("exercise"))
            model.addAttribute("exercise", new Exercise());

        return "exercise";
    }

    @GetMapping("/getExerciseVolume")
    public @ResponseBody Map<String, Integer> getExerciseVolume(@RequestParam int exerciseId, @RequestParam int limit) {
        List<ExerciseSession> exerciseSessions = exerciseSessionRepository
                .findLastExerciseSessionForExerciseId(exerciseId, limit);
        Map<String, Integer> result = new LinkedHashMap<>();

        exerciseSessions.forEach(exerciseSession ->
                result.put(
                        exerciseSession.getTrainingSession().getCreatedAt().toString(),
                        exerciseSession.getExerciseSerieList().stream()
                                .map(es -> es.getWeight() * es.getReps())
                                .reduce(0, Integer::sum))
        );

        return result;
    }

    @PostMapping
    public String addExercise(@Valid Exercise exercise, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exercise", bindingResult);
            redirectAttributes.addFlashAttribute("exercise", exercise);
        } else {
            exerciseRepository.save(exercise);
        }
        return "redirect:/exercise";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteExercise(@PathVariable int id) {
        exerciseRepository.deleteById(id);
        return "redirect:/exercise";
    }
}
