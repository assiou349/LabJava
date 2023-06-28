package com.labjava.skillguest.api.persistence.entity.dto;



import lombok.Data;

import java.io.Serializable;

@Data
public class Event implements Serializable {
    public enum Type {INTERVIEW_CREATE, ELIGIBLE_TECH_FOUND, NO_TECH_AVAILABLE,INTERVIEW_ACCEPTED
        ,INTERVIEW_ALREADY_ACCEPTED,INTERVIEW_REFUSED;
    }
    private String email;
    private Event.Type eventType;
    private Long interviewId;

}
