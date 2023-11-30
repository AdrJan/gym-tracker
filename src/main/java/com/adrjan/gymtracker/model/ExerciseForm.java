package com.adrjan.gymtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseForm {

    private int exerciseId;
    private List<@NotNull @Positive @Digits(integer = 10, fraction = 0) Integer> reps;
    private List<@NotNull @Positive @Digits(integer = 10, fraction = 0) Integer> weights;
}
