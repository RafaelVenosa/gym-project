package com.gym.authuser.services.impl;

import com.gym.authuser.enums.ActionType;
import com.gym.authuser.models.UserModel;
import com.gym.authuser.publishers.UserEventPublisher;
import com.gym.authuser.repositories.UserRepository;
import com.gym.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Transactional //@Transactional para possibilitar o retorno caso ocorra erros no salvamento de alguma das partes
    @Override
    public UserModel saveUser(UserModel userModel, String routingKey) {
        userModel = save(userModel); //utiliza do método de save tradicional que ja usava antes, para evitar de gerar mais códigos no repository
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE, routingKey);  //utiliza do método publisher para enviar o evento, utilizando de um ponto de injeção lá em cima para poder acessar o método publisher
        return userModel;  //Na linha acima é utilizo o método  convertToUserDto que foi definido no UserModel
    }

    @Transactional
    @Override
    public void deleteUser(UserModel userModel, String routingKey) {
        delete(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.DELETE, routingKey);
    }

    @Transactional
    @Override
    public UserModel updateUser(UserModel userModel, String routingKey) {
        userModel = save(userModel);
        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.UPDATE, routingKey);
        return userModel;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Agendado para executar diariamente à meia-noite
    public void checkUserStatus() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        userRepository.blockExpiredUsers(now);
    }


}
