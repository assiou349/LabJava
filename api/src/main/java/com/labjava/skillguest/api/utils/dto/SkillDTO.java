package com.labjava.skillguest.api.utils.dto;

import lombok.Data;

@Data
public class SkillDTO {
    private TechnicalAdvisorDTO technicalAdvisor;
    private JobPositionDTO jobPosition;
    private LevelOfExpertiseDTO levelOfExpertise;
}
