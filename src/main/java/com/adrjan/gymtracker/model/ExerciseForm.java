package com.adrjan.gymtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseForm {

    private int exerciseId;
    private List<RepsForm> repForm;
    private List<WeightForm> weightForm;
}
