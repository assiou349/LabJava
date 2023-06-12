package com.labjava.skillguest.api.utils.dto;

import lombok.Data;

@Data
public class InterviewTechAdvisorDto {

    private String requesterName;
    private String requesterEmail;
    private String jobPosition;
    private String levelOfExpertise;
    private String description;
    private boolean urgent;
    private String name;
    private String email;
}
