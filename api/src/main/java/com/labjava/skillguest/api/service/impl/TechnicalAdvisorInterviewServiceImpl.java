package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisorInterview;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorInterviewRepository;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorRepository;
import com.labjava.skillguest.api.service.interfaces.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class TechnicalAdvisorInterviewServiceImpl extends AbstractService<TechnicalAdvisorInterview>  implements TechnicalAdvisorInterviewService {

    private final TechnicalAdvisorInterviewRepository advisorInterviewRepository;
    private final SkillService SkillService;
    private final InterviewRepository interviewRepository;
    private final TechnicalAdvisorRepository technicalAdvisorRepository;
    private final MessagingService messagingService;


    public TechnicalAdvisorInterviewServiceImpl(TechnicalAdvisorInterviewRepository advisorInterviewRepository,
                                                SkillService skillService, InterviewRepository interviewRepository,
                                                TechnicalAdvisorRepository technicalAdvisorRepository,
                                                @Qualifier("technicalAdvisorMessageProducer")MessagingService messagingService) {
        super();
        this.advisorInterviewRepository = advisorInterviewRepository;
        SkillService = skillService;
        this.interviewRepository = interviewRepository;
        this.technicalAdvisorRepository = technicalAdvisorRepository;
        this.messagingService = messagingService;
    }


    @Override
    protected JpaRepository<TechnicalAdvisorInterview, Long> getDao() {
        return advisorInterviewRepository;
    }

    @Override
    public TechnicalAdvisorInterview save(TechnicalAdvisorInterview technicalAdvisorInterview) {
        return save(technicalAdvisorInterview);
    }

    @Override
    public void askInterviewAssignment(Long techAdvisorId, Long interviewID, boolean accepted) {
        Interview interview = interviewRepository.findById(interviewID).orElse(null);
        if (interview != null) {
            TechnicalAdvisor technicalAdvisor = technicalAdvisorRepository.findById(techAdvisorId).orElse(null);
            if (null != null) {
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
    }
}
