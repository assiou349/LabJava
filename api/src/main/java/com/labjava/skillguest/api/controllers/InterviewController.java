package com.labjava.skillguest.api.controllers;

import com.labjava.skillguest.api.service.InterviewServiceInterface;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {
    @Autowired
    private InterviewServiceInterface interviewService;


    @PostMapping
    public String createInterview(@RequestBody InterviewDto interviewDto) {
        interviewService.addInterview(interviewDto);
        return "Interview added to DB";
    }

}
