package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InterviewServiceImpl extends AbstractService<Interview>  implements InterviewService {

    @Autowired
    private  JobPositionRepository jobPositionRepository;
    @Autowired
    private  InterviewRepository interviewRepository;
    @Autowired
    private  MessagingService messagingService;

    private final EntityMapper entityMapper;

    public InterviewServiceImpl(EntityMapper entityMapper) {
        super();
        this.entityMapper = entityMapper;

    }


    @Override
    protected final InterviewRepository getDao() {
        return interviewRepository;
    }

    @Override
    public InterviewDto saveInterview(InterviewDto interviewDto) {
        JobPosition jobPosition = jobPositionRepository.findByName(interviewDto.getJobPosition());
        if (jobPosition == null) {
            throw new RuntimeException("JobPosition not found: " + interviewDto.getJobPosition());
        }
        Interview interview = entityMapper.toInterview(interviewDto);
        interview.setJobPosition(jobPosition);
        Interview interviewSaved = getDao().save(interview);
        interviewDto.setId(interviewSaved.getId());

        Event event =  new Event();
        event.setEventType((Event.Type.INTERVIEW_CREATE));
        event.setActor(interview.getRequesterEmail());
        event.setData(interview.getJobPosition().getName());

        messagingService.sendMessage( event);

        return interviewDto;
    }

    @Override
    public Interview getInterviewsNotProcessedFromActor(String actor) {
        return getDao().findAllByRequesterEmailAndTechnicalAdvisorIsNull(actor);
    }
}
