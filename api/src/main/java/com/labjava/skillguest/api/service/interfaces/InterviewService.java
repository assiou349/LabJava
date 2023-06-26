package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.utils.dto.InterviewDto;


public interface InterviewService  extends IService<Interview> {
    InterviewDto saveInterview(InterviewDto interviewDto);

    Interview getInterviewsNotProcessedFromActor(String actor);
}
