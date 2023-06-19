package com.labjava.skillguest.api.service.integration;

import com.labjava.skillguest.api.persistence.interfaces.INotificationEntity;

import java.io.Serializable;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class Event<T extends INotificationEntity> {
    public enum Type {CREATE, FOUND, NOTFOUND,  ACCEPTED}

    private Event.Type eventType;
    private T data;

    public Event() {
        this.eventType = null;
        this.data = null;
    }

    public Event(Type eventType, T data) {
        this.eventType = eventType;
        this.data = data;
    }

    public Type getEventType() {
        return eventType;
    }

    public T getData() {
        return data;
    }

}
