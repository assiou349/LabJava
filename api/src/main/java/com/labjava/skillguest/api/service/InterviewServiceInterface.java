package com.labjava.skillguest.api.service;

import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.exception.NotFoundException;

public interface InterviewServiceInterface {
    InterviewDto addInterview(InterviewDto interviewDto);

    //InterviewDto getInterviewById(Long id) throws NotFoundException;
}
