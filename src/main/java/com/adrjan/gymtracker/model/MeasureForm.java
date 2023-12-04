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

    @NotNull @Positive
    private double leftBiceps;
    @NotNull @Positive
    private double rightBiceps;
    @NotNull @Positive
    private double chest;
    @NotNull @Positive
    private double waist;
    @NotNull @Positive
    private double leftThigh;
    @NotNull @Positive
    private double rightThigh;
}
