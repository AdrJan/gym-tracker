package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.entity.Exercise;
import com.adrjan.gymtracker.entity.ExerciseSerie;
import com.adrjan.gymtracker.entity.ExerciseSession;
import com.adrjan.gymtracker.entity.TrainingSession;
import com.adrjan.gymtracker.model.TrainingSessionForm;
import com.adrjan.gymtracker.repositories.ExerciseSerieRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/add-training")
public class AddTrainingController {
    @Autowired
    ExerciseSerieRepository exerciseSerieRepository;

    @GetMapping
    public String showPage(Model model) {
        if (!model.containsAttribute("trainingSessionForm"))
            model.addAttribute("trainingSessionForm", new TrainingSessionForm());
        return "add-training";
    }

    @PostMapping
    public String postData(@ModelAttribute @Valid TrainingSessionForm trainingSessionForm,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.trainingSessionForm", bindingResult);
            redirectAttributes.addFlashAttribute("trainingSessionForm", trainingSessionForm);
            return "redirect:/add-training";
        }

        TrainingSession trainingSession = new TrainingSession();
        List<ExerciseSerie> exerciseSerieList = new ArrayList<>();

        trainingSessionForm.setTrainingSessionForm(
                trainingSessionForm
                        .getTrainingSessionForm()
                        .stream()
                        .filter(tsf -> tsf.getExerciseId() != 0)
                        .collect(Collectors.toList()));

        trainingSessionForm.getTrainingSessionForm().forEach(
                exerciseForm -> {
                    Exercise exercise = Exercise.builder()
                            .id(exerciseForm.getExerciseId())
                            .name("nothing")
                            .build();
                    ExerciseSession tempExerciseSession = ExerciseSession.builder()
                            .trainingSession(trainingSession)
                            .exercise(exercise)
                            .build();

                    for (int i = 0; i < exerciseForm.getReps().size(); i++) {
                        exerciseSerieList.add(
                                ExerciseSerie.builder()
                                        .id(0)
                                        .reps(exerciseForm.getReps().get(i))
                                        .weight(exerciseForm.getWeights().get(i))
                                        .exerciseSession(tempExerciseSession)
                                        .build());
                    }
                }
        );
        try {
            exerciseSerieRepository.saveAll(exerciseSerieList);
            redirectAttributes.addFlashAttribute("message", "Trening został pomyślnie dodany.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Wystąpił błąd poczas próby dodania treningu.");
        }
        return "redirect:/add-training";
    }
}
