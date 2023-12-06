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
    private Double leftBiceps;
    @NotNull @Positive
    private Double rightBiceps;
    @NotNull  @Positive
    private Double chest;
    @NotNull @Positive
    private Double waist;
    @NotNull @Positive
    private Double leftThigh;
    @NotNull @Positive
    private Double rightThigh;
}
