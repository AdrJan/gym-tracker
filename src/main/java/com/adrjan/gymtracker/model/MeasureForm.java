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

    // TODO Dodać odpowiednie komunikaty w odpowiedniej chwili.
    // Obecnie wali komunikatem 'nie może mieć wartości null'.

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
    @NotNull @Positive
    private Double weight;
}
