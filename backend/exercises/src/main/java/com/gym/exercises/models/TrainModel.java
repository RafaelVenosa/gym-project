package com.gym.exercises.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gym.exercises.enums.TrainStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_TRAIN")
public class TrainModel extends RepresentationModel<TrainModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID trainId;
    @Column(nullable = false, length = 50)
    private String trainName;
    @Column(nullable = false, length = 60)
    private String trainCreator;
    @Column(nullable = false)
    private UUID trainerId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainStatus trainStatus;
    @Column(nullable = false, length = 100)
    private String exercise1;
    @Column(nullable = true, length = 100)
    private String exercise2;
    @Column(nullable = true, length = 100)
    private String exercise3;
    @Column(nullable = true, length = 100)
    private String exercise4;
    @Column(nullable = true, length = 100)
    private String exercise5;
    @Column(nullable = true, length = 100)
    private String exercise6;
    @Column(nullable = true, length = 100)
    private String exercise7;
    @Column(nullable = true, length = 100)
    private String exercise8;
    @Column(nullable = true, length = 100)
    private String exercise9;
    @Column(nullable = true, length = 100)
    private String exercise10;
    @Column(nullable = true, length = 100)
    private String exercise11;
    @Column(nullable = true, length = 100)
    private String exercise12;
    @Column(nullable = true, length = 100)
    private String exercise13;
    @Column(nullable = true, length = 100)
    private String exercise14;
    @Column(nullable = true, length = 100)
    private String exercise15;
    @Column(nullable = true, length = 100)
    private String exercise16;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime expirationDate;

}
