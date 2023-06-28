package com.labjava.skillguest.api.persistence.entity.dto;

import lombok.Data;

@Data
public class SkillDto {
    private TechnicalAdvisorDto technicalAdvisor;
    private JobPositionDto jobPosition;
    private LevelOfExpertiseDto levelOfExpertise;
}
