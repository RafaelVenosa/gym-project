package com.gym.authuser.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gym.authuser.dtos.UserEventDto;
import com.gym.authuser.enums.UserStatus;
import com.gym.authuser.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

    @Data/*o @Data do loombok já traz os métodos getters e setters, reduzindo o quantidade de código a se escrever,também traz os
demais construtores que forem necessários à aplicação. Caso fosse necessário utilizar um get ou um set específico poderia ser feita
a anotação do @Data a nível de atributo, mas para esse caso como todos irão utilizar foi feita a nível de classe*/
    @JsonInclude(JsonInclude.Include.NON_NULL) // Oculta os valores nulos
    @Entity // Define que é uma entidade jpa
    @Table(name = "TB_USERS")
//Serializable realiza a conversão de objetos java em uma sequência de bytes que pode ser salvo no banco de dados
    public class UserModel extends RepresentationModel<UserModel> implements Serializable, UserDetails {
        /*UID = numero de controle de versionamento feito pela jvm, que usa este número
        para fazer o controle e a comparação de cada atributo e cada conversão, que é
        feita para verificar se os dados convertidos realmente pertencem àquela classe.
        A sua declaração é feita automaticamente, caso não declaremos manualmente dessa forma,
        porém a sua declaração manual evita possíveis erros na execução*/
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID userId;
        @Column(nullable = false, unique = true, length = 50)
        private String username;
        @Column(nullable = false, unique = true, length = 70)
        private String userImage;
        @Column(nullable = false, unique = true, length = 50)
        private String email;
        @Column(length = 255)
        @JsonIgnore//ocultar o campo password
        private String password;
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private UserStatus userStatus;
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private UserType userType;
        @Column(nullable = false, length = 20)
        private String phoneNumber;
        @Column(nullable = false, length = 20)
        private String cpf;
        @Column(nullable = false)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime registrationDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime expirationDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime lastUpdateDate;

        public UserEventDto convertToUserEventDto() {
            var userEventDto = new UserEventDto();
            BeanUtils.copyProperties(this, userEventDto);
            userEventDto.setUserStatus(this.getUserStatus().toString());
            return userEventDto;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            if(this.userType == UserType.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_STAFF"), new SimpleGrantedAuthority("ROLE_USER"));
            if(this.userType == UserType.INSTRUCTOR) return List.of(new SimpleGrantedAuthority("ROLE_STAFF"), new SimpleGrantedAuthority("ROLE_USER"));
            else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

