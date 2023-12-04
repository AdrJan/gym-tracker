package com.adrjan.gymtracker.repositories;

import com.adrjan.gymtracker.entity.ExerciseSerie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExerciseSerieRepository extends CrudRepository<ExerciseSerie, Integer> {
}
