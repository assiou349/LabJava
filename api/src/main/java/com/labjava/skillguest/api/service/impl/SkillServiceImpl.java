package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.entity.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.entity.Skill;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.SkillRepository;
import com.labjava.skillguest.api.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillRepository skillRepository;
    @Override
    public List<Skill> getSkillByJobPositionAndLevelOfExpertise(JobPosition jobPosition, LevelOfExpertise levelOfExpertise) {
        return skillRepository.findAllByJobPositionAndLevelOfExpertiseLessThan(jobPosition, levelOfExpertise);
    }
}
