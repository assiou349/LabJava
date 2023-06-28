package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.dto.InterviewDto;


public interface InterviewService  extends IService<Interview> {
    InterviewDto saveInterview(InterviewDto interviewDto);

    InterviewDto updateInterview(InterviewDto interviewDto);

    Interview getInterviewsNotProcessedFromActor(String actor);

    Interview getById(Long id);

    void assignInterviewToTechAdvisor(Long interviewId, String email);
}
