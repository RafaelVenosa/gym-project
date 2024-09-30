package com.gym.exercises.services.impl;

import com.gym.exercises.models.UserModel;
import com.gym.exercises.repositories.UserRepository;
import com.gym.exercises.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(UserModel userModel) {
        userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public List<UserModel> findByIdIn(List<UUID> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Agendado para executar diariamente à meia-noite
    public void checkUserStatus() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        userRepository.blockExpiredUsers(now);
    }
}
