package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.JobPositionService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class InterviewServiceImpl extends AbstractService<Interview>  implements InterviewService {

    private final JobPositionService jobPositionService;
    private final InterviewRepository interviewRepository;
    private final MessagingService messagingService;
    private final TechnicalAdvisorService technicalAdvisorService;

    private final EntityMapper entityMapper;

    public InterviewServiceImpl(EntityMapper entityMapper, JobPositionService jobPositionService, InterviewRepository interviewRepository, @Qualifier("technicalAdvisorMessageProducer")MessagingService messagingService, TechnicalAdvisorService technicalAdvisorService) {
        super();
        this.entityMapper = entityMapper;

        this.jobPositionService = jobPositionService;
        this.interviewRepository = interviewRepository;
        this.messagingService = messagingService;
        this.technicalAdvisorService = technicalAdvisorService;
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
        event.setActor(interview.getRequesterEmail());
        event.setData(interview.getJobPosition().getName());

        messagingService.sendMessage( event);

        return interviewDto;
    }

    @Override
    public InterviewDto updateInterview(InterviewDto interviewDto) {
        Event event =  new Event();

        Interview interview = getById(interviewDto.getId());
        if (null == interview.getTechnicalAdvisor()) {
            TechnicalAdvisor advisorServiceById = technicalAdvisorService.getById(interviewDto.getIdTechAdvisor());
            advisorServiceById.setActive(false);
            interview.setTechnicalAdvisor(advisorServiceById);
            update(interview);
            event.setEventType((Event.Type.INTERVIEW_ACCEPTED));

        }
        event.setEventType((Event.Type.INTERVIEW_ALREADY_ACCEPTED));

        event.setActor(interview.getTechnicalAdvisor().getEmail());
        event.setData(interview.getJobPosition().getName());
        messagingService.sendMessage( event);

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
}
