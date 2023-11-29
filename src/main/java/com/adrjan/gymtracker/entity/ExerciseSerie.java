package com.adrjan.gymtracker.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ExerciseSerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int reps;
    private int weight;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_session_id")
    private ExerciseSession exerciseSession;
}
