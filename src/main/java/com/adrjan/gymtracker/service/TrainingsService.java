package com.adrjan.gymtracker.service;

import com.adrjan.gymtracker.entity.TrainingSession;
import com.adrjan.gymtracker.repositories.TrainingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingsService {

    @Autowired
    TrainingsRepository trainingsRepository;

    public Optional<TrainingSession> getTrainingSessionById(int id) {
        return trainingsRepository.findById(id);
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingsRepository.findAllByOrderByCreatedAtDesc();
    }
}
