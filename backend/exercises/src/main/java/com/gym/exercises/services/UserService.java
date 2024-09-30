package com.gym.exercises.services;

import com.gym.exercises.models.TrainModel;
import com.gym.exercises.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void save(UserModel userModel);

    void delete(UUID userId);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    List<UserModel> findByIdIn(List<UUID> userIds);
}
