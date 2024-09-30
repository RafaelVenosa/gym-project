package com.gym.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.UUID;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    /*public UserDto() {
    }*/

    public interface UserView{
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}

        public static interface LoginPost {}
    }

    private UUID userId;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    private String username;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = {UserView.RegistrationPost.class, UserView.UserPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String userImage;

    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.LoginPost.class})
    @Email(groups = {UserView.RegistrationPost.class, UserView.LoginPost.class})
    @JsonView({UserView.RegistrationPost.class, UserView.LoginPost.class})
    private String email;

    @NotBlank(groups = {UserView.PasswordPut.class, UserView.LoginPost.class})
    @Size(min = 6, max = 20, groups = {UserView.PasswordPut.class, UserView.UserPut.class, UserView.LoginPost.class})
    @JsonView({UserView.PasswordPut.class, UserView.UserPut.class, UserView.LoginPost.class})
    private String password;

    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6, max = 20, groups = UserView.PasswordPut.class)
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String phoneNumber;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private String cpf;

}
