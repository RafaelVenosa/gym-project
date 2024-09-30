package com.gym.exercises.dtos;

import com.gym.exercises.enums.TrainStatus;
import com.gym.exercises.enums.TrainerStatus;
import com.gym.exercises.enums.UserStatus;
import com.gym.exercises.models.TrainerModel;
import com.gym.exercises.models.UserModel;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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

    //método que converte o usereventdto em usermodel
    public UserModel convertToUserModel(){
        var userModel = new UserModel();
        BeanUtils.copyProperties(this, userModel);
        userModel.setUserStatus(UserStatus.valueOf(this.getUserStatus()));
        /*var userEventDto = new UserEventDto();
        userModel.setUserId(userEventDto.getUserId());
        userModel.setUsername(userEventDto.getUsername());
        userModel.setCpf(userEventDto.getCpf());
        userModel.setUserStatus(UserStatus.valueOf(userEventDto.getUserStatus()));*/
        return userModel;
    }

    //método que converte o usereventdto em trainermodel
    public TrainerModel convertToTrainerModel(){
        var trainerModel = new TrainerModel();
        trainerModel.setTrainerName(this.getUsername());
        trainerModel.setTrainerId(this.getUserId());
        trainerModel.setTrainerStatus(TrainerStatus.valueOf(this.getUserStatus()));
        trainerModel.setUserImage(this.getUserImage());
        trainerModel.setExpirationDate(this.getExpirationDate());
        return trainerModel;
    }

}
