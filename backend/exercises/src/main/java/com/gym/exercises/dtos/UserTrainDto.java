package com.gym.exercises.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTrainDto {

    private UUID userId;

    private UUID trainId;

}
