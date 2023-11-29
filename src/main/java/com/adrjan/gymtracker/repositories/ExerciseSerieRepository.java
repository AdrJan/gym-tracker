package com.adrjan.gymtracker.repositories;

import com.adrjan.gymtracker.entity.ExerciseSerie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseSerieRepository extends CrudRepository<ExerciseSerie, Integer> {

    @Query(value = "SELECT exercise_session_id, MAX(weight) FROM exercise_serie e \n" +
            "JOIN exercise_session es ON e.exercise_session_id=es.id WHERE es.exercise_id=?1\n" +
            "GROUP BY exercise_session_id ORDER BY exercise_session_id", nativeQuery = true)
    List<Integer> findLastMaxWeightsForExerciseId(Integer exerciseId);
}
