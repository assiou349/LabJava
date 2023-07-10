package com.labjava.skillguest.api.web.controllers;

import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorInterviewService;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technical-advisor-interview")
public class TechnicalAdvisorInterviewController {
    @Autowired
    private TechnicalAdvisorInterviewService advisorInterviewService;


    @PutMapping( "/{techAdvisorId}/{interviewID}" )
    @ResponseStatus(HttpStatus.OK)
    public void askInterviewAssignment(@PathVariable("techAdvisorId")  Long techAdvisorId,
                                                    @PathVariable("interviewID") Long interviewID,
                                                    @RequestBody boolean accepted) {
        advisorInterviewService.askInterviewAssignment(techAdvisorId,interviewID,accepted );
    }



}
