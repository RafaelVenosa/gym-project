package com.gym.authuser.repositories;

import com.gym.authuser.models.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {
    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<UserModel> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel u SET u.userStatus = 'BLOCKED' " +
            "WHERE u.expirationDate < :now AND u.userStatus = 'ACTIVE' AND u.userType = 'CUSTOMER'")
    void blockExpiredUsers(LocalDateTime now);
}
