package com.gym.exercises.services.impl;

import com.gym.exercises.models.TrainModel;
import com.gym.exercises.repositories.TrainRepository;
import com.gym.exercises.services.TrainService;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    TrainRepository trainRepository;

    @Override
    public void save(TrainModel trainModel) {
        trainRepository.save(trainModel);
    }

    @Override
    public Optional<TrainModel> findById(UUID trainId) {
        return trainRepository.findById(trainId);
    }

    @Override
    public Optional<TrainModel> findByTrainerId(UUID trainerId) {
        return trainRepository.findByTrainerId(trainerId);
    }

    @Override
    public void delete(TrainModel trainModel) {
        trainRepository.delete(trainModel);
    }

    @Override
    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Override
    public List<TrainModel> findByIdIn(List<UUID> trainIds) {
        return trainRepository.findByIdIn(trainIds);
    }

    @Override
    public Page<TrainModel> findAll(Specification<TrainModel> spec, Pageable pageable) {
        return trainRepository.findAll(spec, pageable);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Agendado para executar diariamente à meia-noite
    public void checkTrainStatus() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        trainRepository.blockExpiredTrains(now);
    }

}
