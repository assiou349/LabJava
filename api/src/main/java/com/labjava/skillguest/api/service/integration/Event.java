package com.labjava.skillguest.api.service.integration;



import java.io.Serializable;

public class Event implements Serializable {
    public enum Type {INTERVIEW_CREATE, ELIGIBLE_TECH_FOUND, NO_TECH_AVAILABLE}
    private String actor;
    private Event.Type eventType;
    private String data;

    public Event() {
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Type getEventType() {
        return eventType;
    }

    public void setEventType(Type eventType) {
        this.eventType = eventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
