package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.Skill;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorRepository;
import com.labjava.skillguest.api.service.SkillService;
import com.labjava.skillguest.api.service.TechnicalAdvisorService;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicalAdvisorServiceImpl implements TechnicalAdvisorService {

    @Autowired
    private SkillService SkillService;

    private final EntityMapper entityMapper;

    public TechnicalAdvisorServiceImpl(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }


    @Override
    public List<TechnicalAdvisor> findEligibleAdvisors(Interview interview) {
        return SkillService.getSkillByJobPositionAndLevelOfExpertise(interview.getJobPosition(), interview.getLevelOfExpertise())
                .stream().map( Skill::getTechnicalAdvisor).collect(Collectors.toList());
    }
}
