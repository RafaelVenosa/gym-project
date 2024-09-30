package com.gym.exercises.repositories;

import com.gym.exercises.models.TrainModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainRepository extends JpaRepository<TrainModel, UUID>, JpaSpecificationExecutor<TrainModel> {

    @Query(value="select * from tb_train where trainer_id = :trainerId", nativeQuery = true)
    Optional<TrainModel> findByTrainerId(@Param("trainerId") UUID trainerId);

    @Query("SELECT t FROM TrainModel t WHERE t.trainId IN :trainIds")
    List<TrainModel> findByIdIn(@Param("trainIds") List<UUID> trainIds);

    @Modifying
    @Transactional
    @Query("UPDATE TrainModel t SET t.trainStatus = 'BLOCKED' " +
            "WHERE t.expirationDate < :now AND t.trainStatus = 'ACTIVE'")
    void blockExpiredTrains(LocalDateTime now);
}
