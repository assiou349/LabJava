package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.service.impl.MessagingServiceImpl;
import com.labjava.skillguest.api.service.integration.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("technicalAdvisorMessageProducer")
public class TechnicalAdvisorMessageProducer implements MessagingService {

    private final MessagingServiceImpl messagingService;

    @Value("${spring.kafka.consumer.topic}")
    private String topicName;

    public TechnicalAdvisorMessageProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.messagingService = new MessagingServiceImpl( topicName, kafkaTemplate);
    }

    @Override
    public void sendMessage(Event message) {
        messagingService.sendMessage(message);
    }


}
