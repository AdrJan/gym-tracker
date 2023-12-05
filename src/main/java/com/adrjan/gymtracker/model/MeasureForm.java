package com.adrjan.gymtracker.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasureForm {

    @NotNull(message = "Pole nie może być puste!") @Positive
    private Double leftBiceps;
    @NotNull(message = "Pole nie może być puste!") @Positive
    private Double rightBiceps;
    @NotNull(message = "Pole nie może być puste!") @Positive
    private Double chest;
    @NotNull(message = "Pole nie może być puste!") @Positive
    private Double waist;
    @NotNull(message = "Pole nie może być puste!") @Positive
    private Double leftThigh;
    @NotNull(message = "Pole nie może być puste!") @Positive
    private Double rightThigh;
}
