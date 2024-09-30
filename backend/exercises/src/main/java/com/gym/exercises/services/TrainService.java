package com.gym.exercises.services;

import com.gym.exercises.models.TrainModel;
import com.gym.exercises.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainService {

    void save(TrainModel trainModel);

    Optional<TrainModel> findById(UUID trainId);

    Optional<TrainModel> findByTrainerId(UUID trainerId);

    void delete(TrainModel trainModel);

    List<TrainModel> findByIdIn(List<UUID> trainIds);

    Page findAll(Specification<TrainModel> spec, Pageable pageable);

    String[] getNullPropertyNames(Object source);
}
