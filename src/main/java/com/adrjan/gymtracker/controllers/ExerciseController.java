package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Exercise;
import com.adrjan.gymtracker.entity.ExerciseSession;
import com.adrjan.gymtracker.model.ExerciseForm;
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
        if (!model.containsAttribute("exerciseForm"))
            model.addAttribute("exerciseForm", new ExerciseForm());

        return "exercise";
    }

    @GetMapping("/getExerciseVolume")
    public @ResponseBody Map<String, Integer> getExerciseVolume(@RequestParam int exerciseId, @RequestParam int limit) {
        List<ExerciseSession> exerciseSessions = exerciseSessionRepository
                .findLastExerciseSessionForExerciseId(exerciseId, limit);
        Map<String, Integer> result = new LinkedHashMap<>();

        exerciseSessions.forEach(exerciseSession ->
                result.put(
                        String.valueOf(exerciseSession.getTrainingSession().getId()),
                        exerciseSession.getExerciseSerieList().stream()
                                .map(es -> es.getWeight() * es.getReps())
                                .reduce(0, Integer::sum))
        );

        return result;
    }

    @PostMapping
    public String addExercise(@ModelAttribute @Valid ExerciseForm exerciseForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.exerciseForm", bindingResult);
            redirectAttributes.addFlashAttribute("exerciseForm", exerciseForm);
        } else {
            exerciseRepository.save(new Exercise(0, exerciseForm.getName(), new ArrayList<>()));
        }
        return "redirect:/exercise";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteExercise(@PathVariable int id) {
        exerciseRepository.deleteById(id);
        return "redirect:/exercise";
    }
}
