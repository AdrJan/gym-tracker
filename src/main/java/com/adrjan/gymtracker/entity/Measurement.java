package com.adrjan.gymtracker.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
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
    private double weight;

    private LocalDate createdAt;

    @PrePersist
    private void setDate() {
        createdAt = LocalDateTime.now().toLocalDate();
    }
}
