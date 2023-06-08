package com.labjava.skillguest.api.service.integration;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void receiveMessage(String message) {
        // Traitement du message reçu
        System.out.println("Message reçu : " + message);
    }
}
