package com.gym.authuser.enums;

public enum UserType {
    CUSTOMER("CUSTOMER"),
    INSTRUCTOR("INSTRUCTOR"),
    ADMIN("ADMIN");

    private String role;

    UserType(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

}
