package com.gym.exercises.repositories;

import com.gym.exercises.models.TrainerModel;
import com.gym.exercises.models.UserTrainModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface UserTrainRepository extends JpaRepository<UserTrainModel, UUID>, JpaSpecificationExecutor<UserTrainModel> {
    List<UserTrainModel> findByUserId(UUID userId);

    List<UserTrainModel> findByTrainId(UUID trainId);
}
