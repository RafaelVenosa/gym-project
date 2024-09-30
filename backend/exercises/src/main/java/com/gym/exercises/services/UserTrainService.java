package com.gym.exercises.services;

import com.gym.exercises.models.TrainModel;
import com.gym.exercises.models.UserTrainModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTrainService {


    void save(UserTrainModel userTrainModel);

    Optional<UserTrainModel> findByTrainId(UUID trainId);

    void delete(UserTrainModel userTrainModel);


    List<UserTrainModel> findByUserId(UUID userId);

    List<UserTrainModel> findByTrainIdList(UUID trainId);
}
