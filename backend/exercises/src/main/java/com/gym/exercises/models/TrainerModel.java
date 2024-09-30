package com.gym.exercises.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gym.exercises.enums.TrainerStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_TRAINERS")
public class TrainerModel extends RepresentationModel<TrainerModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID trainerId;
    @Column(nullable = false, unique = true, length = 50)
    private String trainerName;
    @Column(nullable = false, unique = true, length = 70)
    private String userImage;
    @Enumerated(EnumType.STRING)
    private TrainerStatus trainerStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expirationDate;

}
