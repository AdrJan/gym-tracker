package com.adrjan.gymtracker.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double leftBiceps;
    private double rightBiceps;
    private double chest;
    private double waist;
    private double leftThigh;
    private double rightThigh;
}
