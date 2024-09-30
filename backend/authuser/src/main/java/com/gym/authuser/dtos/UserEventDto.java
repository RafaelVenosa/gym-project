package com.gym.authuser.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserEventDto {

    private UUID userId;
    private String username;
    private String userImage;
    private String cpf;
    private String userStatus;
    private String actionType;
    private LocalDateTime expirationDate;

}
