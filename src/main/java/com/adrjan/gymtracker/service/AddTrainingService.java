package com.adrjan.gymtracker.service;

import com.adrjan.gymtracker.entity.Exercise;
import com.adrjan.gymtracker.entity.ExerciseSerie;
import com.adrjan.gymtracker.entity.ExerciseSession;
import com.adrjan.gymtracker.entity.TrainingSession;
import com.adrjan.gymtracker.model.TrainingSessionForm;
import com.adrjan.gymtracker.repositories.ExerciseSerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddTrainingService {

    @Autowired
    ExerciseSerieRepository exerciseSerieRepository;

    public Iterable<ExerciseSerie> addNewTraining(TrainingSessionForm trainingSessionForm) {
        TrainingSession trainingSession = new TrainingSession();
        List<ExerciseSerie> exerciseSerieList = new ArrayList<>();

        trainingSessionForm.setTrainingSessionForm(
                trainingSessionForm
                        .getTrainingSessionForm()
                        .stream()
                        .filter(tsf -> tsf.getExerciseId() != 0)
                        .collect(Collectors.toList()));

        trainingSessionForm.getTrainingSessionForm().forEach(
                exerciseForm -> {
                    Exercise exercise = Exercise.builder()
                            .id(exerciseForm.getExerciseId())
                            .name("nothing")
                            .build();
                    ExerciseSession tempExerciseSession = ExerciseSession.builder()
                            .trainingSession(trainingSession)
                            .exercise(exercise)
                            .build();

                    for (int i = 0; i < exerciseForm.getReps().size(); i++) {
                        exerciseSerieList.add(
                                ExerciseSerie.builder()
                                        .id(0)
                                        .reps(exerciseForm.getReps().get(i))
                                        .weight(exerciseForm.getWeights().get(i))
                                        .exerciseSession(tempExerciseSession)
                                        .build());
                    }
                }
        );

        return exerciseSerieRepository.saveAll(exerciseSerieList);
    }
}
