package com.gym.authuser.dtos;


import lombok.Data;


public record ResponseDto (String username, String token, String userId, String userType){

}
