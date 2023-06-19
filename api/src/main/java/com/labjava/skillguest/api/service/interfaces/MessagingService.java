package com.labjava.skillguest.api.service.interfaces;


public interface MessagingService {
    void sendMessage(String topic, String message);
    void consumeMessage(String topic);
}
