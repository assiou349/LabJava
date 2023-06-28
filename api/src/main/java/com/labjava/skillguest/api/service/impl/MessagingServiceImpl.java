package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import org.springframework.kafka.core.KafkaTemplate;

public record MessagingServiceImpl(String topicName,
                                   KafkaTemplate<String, Event> kafkaTemplate) implements MessagingService {

    @Override
    public void sendMessage(Event message) {
        kafkaTemplate.send(topicName, message);
        kafkaTemplate.flush();
    }
}
