package com.gym.exercises.services;

import com.gym.exercises.models.TrainerModel;

import java.util.UUID;

public interface TrainerService {


    String findNameById(UUID trainerId);

    void save(TrainerModel trainerModel);

    void delete(UUID trainerId);
}
