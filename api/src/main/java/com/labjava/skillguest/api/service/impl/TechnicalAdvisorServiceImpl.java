package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.Skill;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorRepository;
import com.labjava.skillguest.api.service.interfaces.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TechnicalAdvisorServiceImpl extends AbstractService<TechnicalAdvisor> implements TechnicalAdvisorService {

    private final SkillService SkillService;
    private final InterviewRepository interviewRepository;
    private final TechnicalAdvisorRepository technicalAdvisorRepository;
    private final MessagingService messagingService;



    public TechnicalAdvisorServiceImpl(SkillService SkillService,
                                       InterviewRepository interviewRepository, TechnicalAdvisorRepository technicalAdvisorRepository,
                                       @Qualifier("technicalAdvisorMessageProducer") MessagingService messagingService) {
        this.SkillService = SkillService;
        this.interviewRepository = interviewRepository;
        this.technicalAdvisorRepository = technicalAdvisorRepository;
        this.messagingService = messagingService;
    }

    @Override
    public List<TechnicalAdvisor> findEligibleAdvisors(Interview interview) {
        return SkillService.getAllSkillByJobPositionOrSuperiorJobPositonMatchingLevelOfExpertise(interview.getJobPosition(), interview.getLevelOfExpertise())
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
    public TechnicalAdvisor updateAdvisor(TechnicalAdvisor technicalAdvisor) {
        return updateAdvisor(technicalAdvisor);
    }

    @Override
    public void askInterviewAssignment(Long techAdvisorId, Long interviewID, boolean accepted) {
        Interview interview = interviewRepository.findById(interviewID).orElse(null);
        if (interview != null) {
            TechnicalAdvisor technicalAdvisor = getById(techAdvisorId);
            Event event =  new Event();
            event.setInterviewId(interviewID);
            event.setEmail(technicalAdvisor.getEmail());
            if ( technicalAdvisor != null  && accepted) {
                event.setEventType((Event.Type.INTERVIEW_ACCEPTED));
            } else {
                event.setEventType((Event.Type.INTERVIEW_REFUSED));
            }
            messagingService.sendMessage(event);
        }
    }

    @Override
    protected JpaRepository<TechnicalAdvisor, Long> getDao() {
        return technicalAdvisorRepository;
    }


}
