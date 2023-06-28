package com.labjava.skillguest.api.service.interfaces;


import com.labjava.skillguest.api.persistence.entity.dto.Event;

public interface MessagingService {
    void sendMessage( Event message);
}
