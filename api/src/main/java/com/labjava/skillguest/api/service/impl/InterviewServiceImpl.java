package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.JobPositionService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.persistence.entity.dto.InterviewDto;
import com.labjava.skillguest.api.service.mail.EmailService;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class InterviewServiceImpl extends AbstractService<Interview>  implements InterviewService {

    private final JobPositionService jobPositionService;
    private final InterviewRepository interviewRepository;
    private final MessagingService messagingService;
    private final TechnicalAdvisorService technicalAdvisorService;
    private final EmailService emailService;


    private final EntityMapper entityMapper;

    public InterviewServiceImpl(EntityMapper entityMapper, JobPositionService jobPositionService,
                                InterviewRepository interviewRepository,
                                @Qualifier("interviewMessageProducer") MessagingService messagingService, TechnicalAdvisorService technicalAdvisorService, EmailService emailService) {
        super();
        this.entityMapper = entityMapper;

        this.jobPositionService = jobPositionService;
        this.interviewRepository = interviewRepository;
        this.messagingService = messagingService;
        this.technicalAdvisorService = technicalAdvisorService;
        this.emailService = emailService;
    }


    @Override
    protected final InterviewRepository getDao() {
        return interviewRepository;
    }

    @Override
    public InterviewDto saveInterview(InterviewDto interviewDto) {
        JobPosition jobPosition = jobPositionService.getByName(interviewDto.getJobPosition());
        if (jobPosition == null) {
            throw new RuntimeException("JobPosition not found: " + interviewDto.getJobPosition());
        }
        Interview interview = entityMapper.toInterview(interviewDto);
        interview.setJobPosition(jobPosition);
        Interview interviewSaved = create(interview);
        interviewDto.setId(interviewSaved.getId());

        Event event =  new Event();
        event.setEventType((Event.Type.INTERVIEW_CREATE));
        event.setEmail(interview.getRequesterEmail());
        event.setInterviewId(interview.getId());

        messagingService.sendMessage( event);

        return interviewDto;
    }

    @Override
    public InterviewDto updateInterview(InterviewDto interviewDto) {
        return interviewDto;
    }

    @Override
    public Interview getInterviewsNotProcessedFromActor(String actor) {
        return getDao().findAllByRequesterEmailAndTechnicalAdvisorIsNull(actor);
    }

    @Override
    public Interview getById(Long id) {
        return findOne(id);
    }

    @Override
    public void assignInterviewToTechAdvisor(Long interviewId, String email) {
        Interview interview = getById(interviewId);
        if (null == interview.getTechnicalAdvisor()) {
            TechnicalAdvisor advisor = technicalAdvisorService.getByEmail(email);
            advisor.setActive(false);
            interview.setTechnicalAdvisor(advisor);
            update(interview);
            emailService.send(advisor.getEmail(), interview.getJobPosition().getName(), "Interview Assignee");
            emailService.send(interview.getRequesterEmail(), "Demande d'assignation d'interview accepte", advisor.getName()+ "  "+advisor.getEmail()+" "+interview.getJobPosition().getName());
        }

    }
}
