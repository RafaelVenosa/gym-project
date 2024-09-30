package com.gym.exercises.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    //connectionFactory é a maneira de comunicar a nossa aplicação com o rabbitmq, ele leva em consideração o link de conexão que colocamos no aplication yaml
    //ponto de injeção do publisher para enviar os eventos
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(messageConverter()); //passa no template o message converter que é o método abaixo
        return template;
    }

    //utilizamos esse método sempre que precisamos receber e enviar mensagens, utiliza do jackson e object mapper para fazer essas conversões
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
