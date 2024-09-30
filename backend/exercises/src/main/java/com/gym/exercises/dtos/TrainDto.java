package com.gym.exercises.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainDto {

    private UUID trainId;

    private UUID trainerId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String trainName;

    @NotBlank
    @Size(min = 1, max = 25)
    private String trainCreator;

    @Size(min = 10, max = 100)
    private String exercise1;

    @Size(min = 10, max = 100)
    private String exercise2;

    @Size(min = 10, max = 100)
    private String exercise3;

    @Size(min = 10, max = 100)
    private String exercise4;

    @Size(min = 10, max = 100)
    private String exercise5;

    @Size(min = 10, max = 100)
    private String exercise6;

    @Size(min = 10, max = 100)
    private String exercise7;

    @Size(min = 10, max = 100)
    private String exercise8;

    @Size(min = 10, max = 100)
    private String exercise9;

    @Size(min = 10, max = 100)
    private String exercise10;

    @Size(min = 10, max = 100)
    private String exercise11;

    @Size(min = 10, max = 100)
    private String exercise12;

    @Size(min = 10, max = 100)
    private String exercise13;

    @Size(min = 10, max = 100)
    private String exercise14;

    @Size(min = 10, max = 100)
    private String exercise15;

    @Size(min = 10, max = 100)
    private String exercise16;



}
