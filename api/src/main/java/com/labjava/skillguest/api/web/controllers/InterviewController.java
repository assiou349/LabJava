package com.labjava.skillguest.api.web.controllers;

import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
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

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public InterviewDto updateInterview(@RequestBody InterviewDto interviewDto) {
        return interviewService.updateInterview(interviewDto);
    }

}
