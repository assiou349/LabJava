package com.labjava.skillguest.api.utils.dto;

import lombok.Data;

@Data
public class SkillDto {
    private TechnicalAdvisorDto technicalAdvisor;
    private JobPositionDto jobPosition;
    private LevelOfExpertiseDto levelOfExpertise;
}
