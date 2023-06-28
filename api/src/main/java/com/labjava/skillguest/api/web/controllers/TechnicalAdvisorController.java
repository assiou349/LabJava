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


    @GetMapping( "/{techAdvisorId}/{interviewID}" )
    @ResponseBody
    public void assignInterview(@PathVariable("techAdvisorId")  Long techAdvisorId,
                                                    @PathVariable("interviewID") Long interviewID,
                                                    @RequestBody String accepted) {
         technicalAdvisorService.assignInterview(techAdvisorId,interviewID,accepted );
    }



}
