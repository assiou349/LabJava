package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.service.interfaces.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingServiceImpl implements MessagingService {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Override
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic,message );
        kafkaTemplate.flush();
    }

    @Override
    public void consumeMessage(String topic) {

    }

}
