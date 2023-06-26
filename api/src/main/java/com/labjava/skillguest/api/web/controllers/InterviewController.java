package com.labjava.skillguest.api.web.controllers;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.IOperations;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import com.labjava.skillguest.api.web.abstracts.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interview")
public class InterviewController  {

    private final InterviewService interviewService;

    public InterviewController( InterviewService interviewService) {
        this.interviewService = interviewService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InterviewDto createInterview(@RequestBody InterviewDto interviewDto) {
        return interviewService.saveInterview(interviewDto);
    }

}
