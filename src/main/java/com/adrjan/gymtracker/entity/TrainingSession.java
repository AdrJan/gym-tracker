package com.adrjan.gymtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "trainingSession", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExerciseSession> exerciseSessionList = new ArrayList<>();

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;

    @PrePersist
    private void setDate() {
        createdAt = new Date();
    }
}
