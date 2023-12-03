package com.adrjan.gymtracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSessionForm {

    @NotNull
    private List<@Valid ExerciseSessionForm> trainingSessionForm;
}
