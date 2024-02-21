package com.adrjan.gymtracker.service;

import com.adrjan.gymtracker.entity.Exercise;
import com.adrjan.gymtracker.entity.ExerciseSession;
import com.adrjan.gymtracker.model.ExerciseForm;
import com.adrjan.gymtracker.repositories.ExerciseRepository;
import com.adrjan.gymtracker.repositories.ExerciseSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseSessionRepository exerciseSessionRepository;

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();

        exerciseRepository.findAll().forEach(exercises::add);

        return exercises;
    }

    public Map<String, Integer> getLastExerciseVolumes(Integer exerciseId, Integer limit) {
        Map<String, Integer> result = new LinkedHashMap<>();

        List<ExerciseSession> exerciseSessions = exerciseSessionRepository
                .findLastExerciseSessionForExerciseId(exerciseId, limit);

        exerciseSessions.forEach(exerciseSession ->
                result.put(
                        String.valueOf(exerciseSession.getTrainingSession().getId()),
                        exerciseSession.getExerciseSerieList().stream()
                                .map(es -> es.getWeight() * es.getReps())
                                .reduce(0, Integer::sum))
        );

        return result;
    }

    public void addExercise(ExerciseForm exerciseForm) {
        exerciseRepository.save(Exercise.builder().name(exerciseForm.getName()).build());
    }

    public boolean deleteExercise(Integer id) {
        try {
            exerciseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
