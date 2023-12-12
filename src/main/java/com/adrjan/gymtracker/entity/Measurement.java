package com.adrjan.gymtracker.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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
    private double weight;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;

    @PrePersist
    private void setDate() {
        createdAt = new Date();
    }
}
