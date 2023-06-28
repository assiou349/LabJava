package com.labjava.skillguest.api.service.interfaces;


import com.labjava.skillguest.api.service.integration.Event;

public interface MessagingService {
    void sendMessage( Event message);
}
