package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.entity.Skill;
import com.labjava.skillguest.api.persistence.repository.SkillRepository;
import com.labjava.skillguest.api.service.interfaces.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public List<Skill> getAllSkillByJobPositionOrSuperiorJobPositonMatchingLevelOfExpertise(JobPosition jobPosition, LevelOfExpertise levelOfExpertise) {
        List<JobPosition> jobPositions = new ArrayList<>();
        jobPositions.add(jobPosition);
        while (jobPosition.getSuperiorPosition() != null) {
            jobPositions.add(jobPosition.getSuperiorPosition());
            jobPosition = jobPosition.getSuperiorPosition();
        }
        return skillRepository.findAllByJobPositionInAndLevelOfExpertiseGreaterThanEqual(jobPositions, levelOfExpertise);
    }
}
