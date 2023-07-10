package com.labjava.skillguest.api.web.controllers;

import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technical-advisor")
public class TechnicalAdvisorController {
    @Autowired
    private TechnicalAdvisorService technicalAdvisorService;


    @PutMapping( "/{techAdvisorId}/{interviewID}" )
    @ResponseBody
    public void askInterviewAssignment(@PathVariable("techAdvisorId")  Long techAdvisorId,
                                                    @PathVariable("interviewID") Long interviewID,
                                                    @RequestBody boolean accepted) {
         technicalAdvisorService.askInterviewAssignment(techAdvisorId,interviewID,accepted );
    }



}
