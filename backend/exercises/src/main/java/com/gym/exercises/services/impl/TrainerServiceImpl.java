package com.gym.exercises.services.impl;


import com.gym.exercises.models.TrainerModel;
import com.gym.exercises.repositories.TrainerRepository;
import com.gym.exercises.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    @Override
    public String findNameById(UUID trainerId) {
        return trainerRepository.findNameById(trainerId);
    }

    @Override
    public void save(TrainerModel trainerModel) {
        trainerRepository.save(trainerModel);
    }

    @Override
    public void delete(UUID trainerId) {
        trainerRepository.deleteById(trainerId);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Agendado para executar diariamente à meia-noite
    public void checkTrainerStatus() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        trainerRepository.blockExpiredTrainers(now);
    }
}
