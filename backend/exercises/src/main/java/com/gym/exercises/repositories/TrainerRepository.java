package com.gym.exercises.repositories;

import com.gym.exercises.models.TrainerModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TrainerRepository extends JpaRepository<TrainerModel, UUID>, JpaSpecificationExecutor<TrainerModel> {


    @Query(value="select trainer_name from tb_trainers where trainer_id = :trainerId", nativeQuery = true)
    String findNameById(@Param("trainerId") UUID trainerId);

    @Modifying
    @Transactional
    @Query("UPDATE TrainerModel t SET t.trainerStatus = 'BLOCKED' " +
            "WHERE t.expirationDate < :now AND t.trainerStatus = 'ACTIVE'")
    void blockExpiredTrainers(LocalDateTime now);
}
