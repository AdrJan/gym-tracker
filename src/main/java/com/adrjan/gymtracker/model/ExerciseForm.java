package com.adrjan.gymtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseForm {

    @NotEmpty(message = "Pole nie może być puste!")
    private String name;
}
