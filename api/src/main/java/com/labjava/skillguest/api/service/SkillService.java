package com.labjava.skillguest.api.service;

import com.labjava.skillguest.api.persistence.entity.*;

import java.util.List;

public interface SkillService {
    List<Skill> getSkillByJobPositionAndLevelOfExpertise(JobPosition jobPosition, LevelOfExpertise levelOfExpertise);
}
