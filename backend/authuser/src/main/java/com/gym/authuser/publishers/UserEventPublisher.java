package com.gym.authuser.publishers;

import com.gym.authuser.dtos.UserEventDto;
import com.gym.authuser.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserEventPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${gym.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishUserEvent(UserEventDto userEventDto, ActionType actionType, String routingKey) {
        userEventDto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, routingKey, userEventDto);
        System.out.println("Mensagem Enviada!");
    }
}
