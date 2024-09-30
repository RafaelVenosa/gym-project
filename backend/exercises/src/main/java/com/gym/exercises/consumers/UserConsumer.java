package com.gym.exercises.consumers;

import com.gym.exercises.dtos.UserEventDto;
import com.gym.exercises.enums.ActionType;
import com.gym.exercises.services.TrainerService;
import com.gym.exercises.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    UserService userService;

    @Autowired
    TrainerService trainerService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${gym.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${gym.broker.exchange.userEvent}", type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "userCustomer")
    )


    //método que escuta a fila
    public void listenUserEvent(@Payload UserEventDto userEventDto){
        var userModel = userEventDto.convertToUserModel();  //converte o userdto em usermodel com o método já criado no userEventDto

        switch (ActionType.valueOf(userEventDto.getActionType())){
            case CREATE:
                userService.save(userModel);
                System.out.println("Mensagem recebida");
                break;
            case UPDATE:
                userService.save(userModel);
                System.out.println("Mensagem recebida");
                break;
            case DELETE:
                userService.delete(userEventDto.getUserId());
                System.out.println("Mensagem recebida");
                break;
        }
    }



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${gym.broker.queue.trainerEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${gym.broker.exchange.userEvent}", type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "userTrainer")
    )

    //método que escuta a fila
    public void listenTrainerEvent(@Payload UserEventDto userEventDto){
        var trainerModel = userEventDto.convertToTrainerModel();  //converte o userdto em usermodel com o método já criado no userEventDto

        switch (ActionType.valueOf(userEventDto.getActionType())){
            case CREATE:
                trainerService.save(trainerModel);
                System.out.println("Mensagem recebida");
                break;
            case UPDATE:
                trainerService.save(trainerModel);
                System.out.println("Mensagem recebida");
                break;
            case DELETE:
                trainerService.delete(userEventDto.getUserId());
                System.out.println("Mensagem recebida");
                break;
        }
    }



}
