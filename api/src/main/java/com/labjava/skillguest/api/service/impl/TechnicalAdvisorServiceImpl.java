package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.Skill;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorRepository;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.SkillService;
import com.labjava.skillguest.api.service.TechnicalAdvisorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TechnicalAdvisorServiceImpl extends AbstractService<TechnicalAdvisor> implements TechnicalAdvisorService {

    private final SkillService SkillService;
    private final TechnicalAdvisorRepository technicalAdvisorRepository;


    public TechnicalAdvisorServiceImpl(SkillService SkillService, TechnicalAdvisorRepository technicalAdvisorRepository) {
        this.SkillService = SkillService;
        this.technicalAdvisorRepository = technicalAdvisorRepository;
    }

    @Override
    public List<TechnicalAdvisor> findEligibleAdvisors(Interview interview) {
        return SkillService.getSkillByJobPositionAndLevelOfExpertise(interview.getJobPosition(), interview.getLevelOfExpertise())
                .stream().map( Skill::getTechnicalAdvisor).collect(Collectors.toList());
    }


    @Override
    public TechnicalAdvisor getByEmail(String email) {
        return technicalAdvisorRepository.findByEmail(email);
    }

    @Override
    public TechnicalAdvisor getById(Long id) {
        return findOne(id);
    }

    @Override
    protected JpaRepository<TechnicalAdvisor, Long> getDao() {
        return technicalAdvisorRepository;
    }


}
