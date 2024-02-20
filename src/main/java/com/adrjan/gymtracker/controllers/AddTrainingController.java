package com.adrjan.gymtracker.controllers;

import com.adrjan.gymtracker.model.TrainingSessionForm;
import com.adrjan.gymtracker.service.AddTrainingService;
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
@RequestMapping("/add-training")
public class AddTrainingController {
    @Autowired
    AddTrainingService addTrainingService;

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

        try {
            addTrainingService.addNewTraining(trainingSessionForm);
            redirectAttributes.addFlashAttribute("message", "Trening został pomyślnie dodany.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Wystąpił błąd poczas próby dodania treningu.");
        }
        return "redirect:/add-training";
    }
}
