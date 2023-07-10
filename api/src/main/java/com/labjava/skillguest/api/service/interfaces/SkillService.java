package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.entity.*;
import com.labjava.skillguest.api.utils.LevelOfExpertise;

import java.util.List;

public interface SkillService {
    List<Skill> getAllSkillByJobPositionOrSuperiorJobPositonMatchingLevelOfExpertise(JobPosition jobPosition, LevelOfExpertise levelOfExpertise);
}
