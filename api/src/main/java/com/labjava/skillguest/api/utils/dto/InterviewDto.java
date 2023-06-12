package com.labjava.skillguest.api.utils.dto;


import lombok.Data;
@Data
public class InterviewDto {
    private Long id;
    private String requesterName;
    private String requesterEmail;
    private String jobPosition;
    private String levelOfExpertise;
    private String description;
    private boolean urgent;
}
