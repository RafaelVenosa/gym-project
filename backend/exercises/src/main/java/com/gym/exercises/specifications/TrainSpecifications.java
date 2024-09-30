package com.gym.exercises.specifications;

import java.time.LocalDateTime;
import com.gym.exercises.models.TrainModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class TrainSpecifications {

    public static Specification<TrainModel> trainsByTrainerId(UUID trainerId) {
        return (root, query, criteriaBuilder) -> {
            // Adiciona a condição de igualdade para trainerId
            Predicate trainerIdPredicate = criteriaBuilder.equal(root.get("trainerId"), trainerId);


            // Condição para verificar se o status do treino é ACTIVE
            Predicate activeStatusPredicate = criteriaBuilder.equal(root.get("trainStatus"), "ACTIVE");

            // Adiciona a ordenação por creationDate em ordem descendente
            query.orderBy(criteriaBuilder.asc(root.get("creationDate")));

            // Retorna ambas as condições combinadas com "AND"
            return criteriaBuilder.and(trainerIdPredicate, activeStatusPredicate);
        };
    }

    public static Specification<TrainModel> trainsExpiringIn10Days(UUID trainerId) {
        return (root, query, criteriaBuilder) -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime tenDaysFromNow = now.plusDays(10);

            Predicate trainerIdPredicate = criteriaBuilder.equal(root.get("trainerId"), trainerId);
            Predicate within10Days = criteriaBuilder.between(root.get("expirationDate"), now, tenDaysFromNow);
            query.orderBy(criteriaBuilder.asc(root.get("expirationDate")));

            return criteriaBuilder.and(trainerIdPredicate, within10Days);
        };
    }

    public static Specification<TrainModel> trainsExpired(UUID trainerId) {
        return (root, query, criteriaBuilder) -> {
            LocalDateTime now = LocalDateTime.now();

            Predicate trainerIdPredicate = criteriaBuilder.equal(root.get("trainerId"), trainerId);
            Predicate expired = criteriaBuilder.lessThan(root.get("expirationDate"), now);
            query.orderBy(criteriaBuilder.asc(root.get("expirationDate")));

            return criteriaBuilder.and(trainerIdPredicate, expired);
        };
    }

}
