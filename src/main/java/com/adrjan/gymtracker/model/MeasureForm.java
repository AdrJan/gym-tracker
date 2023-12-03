package com.adrjan.gymtracker.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class MeasureForm {

    @NotNull @Positive
    private double leftBiceps;
    @NotNull @Positive
    private double rightBiceps;
    @NotNull @Positive
    private double chest;
    @NotNull @Positive
    private double waistline;
    @NotNull @Positive
    private double leftThigh;
    @NotNull @Positive
    private double rightThigh;
}
