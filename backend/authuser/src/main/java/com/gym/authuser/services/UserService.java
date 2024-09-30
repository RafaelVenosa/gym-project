package com.gym.authuser.services;

import com.gym.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

    Optional<UserModel> findById(UUID userId);

    void delete(UserModel userModel);

    UserModel save(UserModel userModel);

    UserModel saveUser(UserModel userModel, String routingKey);


    void deleteUser(UserModel userModel, String routingKey);


    UserModel updateUser(UserModel userModel, String routingKey);

}
