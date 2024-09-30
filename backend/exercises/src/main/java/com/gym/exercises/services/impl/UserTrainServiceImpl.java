package com.gym.exercises.services.impl;

import com.gym.exercises.models.UserTrainModel;
import com.gym.exercises.repositories.UserTrainRepository;
import com.gym.exercises.services.UserTrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserTrainServiceImpl implements UserTrainService {

    @Autowired
    UserTrainRepository userTrainRepository;

    @Override
    public void save(UserTrainModel userTrainModel) {
        userTrainRepository.save(userTrainModel);
    }

    @Override
    public Optional<UserTrainModel> findByTrainId(UUID trainId) {
        return userTrainRepository.findById(trainId);
    }

    @Override
    public void delete(UserTrainModel userTrainModel) {
        userTrainRepository.delete(userTrainModel);
    }

    @Override
    public List<UserTrainModel> findByUserId(UUID userId) {
        return userTrainRepository.findByUserId(userId);
    }

    @Override
    public List<UserTrainModel> findByTrainIdList(UUID trainId) {
        return userTrainRepository.findByTrainId(trainId);
    }


}
