package com.labjava.skillguest.api.persistence.entity.dto;

import lombok.Data;

@Data
public class TechnicalAdvisorDto {

    private String name;
    private String email;
    private boolean active;
    private String levelOfExpertise;
}
