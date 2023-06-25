package com.labjava.skillguest.api.utils.dto;


import com.labjava.skillguest.api.persistence.interfaces.INotificationEntity;
import lombok.Data;
@Data
//Todo : pourquoi tes dtos sont dans un package utils ?
public class InterviewDto implements INotificationEntity {
    private Long id;
    private String requesterName;
    private String requesterEmail;
    private String techEmail;
    private String jobPosition;
    private String levelOfExpertise;
    private String description;
    private boolean urgent;

    @Override
    public String getSubjet() {
        return jobPosition;
    }
}
