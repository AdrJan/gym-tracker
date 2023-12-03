package com.adrjan.gymtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProgressController {

    @GetMapping("/progress")
    public String showProgressPage() {
        return "progress";
    }
}
