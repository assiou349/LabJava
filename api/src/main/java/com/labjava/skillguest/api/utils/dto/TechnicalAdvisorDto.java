package com.labjava.skillguest.api.utils.dto;

import lombok.Data;

@Data
public class TechnicalAdvisorDto {

    private String name;
    private String email;
    private boolean active;
    private String levelOfExpertise;
}
