package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingServiceImpl implements MessagingService {


    private final KafkaTemplate<String, Event> kafkaTemplate;
    private  final String  topicName;

    public MessagingServiceImpl(@Value("${spring.kafka.consumer.topic}") final String topicName, KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    ;

    @Override
    public void sendMessage( Event message) {
        kafkaTemplate.send(topicName,message );
        kafkaTemplate.flush();
    }

    @Override
    public void consumeMessage( ) {

    }

}
