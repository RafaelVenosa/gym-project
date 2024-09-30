package com.gym.exercises.repositories;

import com.gym.exercises.models.TrainModel;
import com.gym.exercises.models.TrainerModel;
import com.gym.exercises.models.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    @Modifying
    @Transactional
    @Query("UPDATE UserModel u SET u.userStatus = 'BLOCKED' " +
            "WHERE u.expirationDate < :now AND u.userStatus = 'ACTIVE'")
    void blockExpiredUsers(LocalDateTime now);

    @Query("SELECT u FROM UserModel u WHERE u.userId IN :userIds")
    List<UserModel> findByIdIn(@Param("userIds") List<UUID> userIds);
}
