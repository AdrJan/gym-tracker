package com.adrjan.gymtracker.repositories;

import com.adrjan.gymtracker.entity.ExerciseSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseSessionRepository extends CrudRepository<ExerciseSession, Integer> {

    @Query(value = "SELECT * FROM exercise_session WHERE exercise_session.exercise_id = ?1 ORDER BY exercise_session.id DESC LIMIT ?2", nativeQuery = true)
    List<ExerciseSession> findLastExerciseSessionForExerciseId(Integer exerciseId, Integer limit);
}
