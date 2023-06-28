package com.labjava.skillguest.api.persistence.entity.dto;


import lombok.Data;
@Data
public class InterviewDto   {
    private Long id;
    private Long idTechAdvisor;
    private String requesterName;
    private String requesterEmail;
    private String techEmail;
    private String jobPosition;
    private String levelOfExpertise;
    private String description;
    private boolean urgent;

}
