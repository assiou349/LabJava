package com.labjava.skillguest.api.web.controllers;

import com.labjava.skillguest.api.persistence.entity.LineOfBusiness;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.LineOfBusinessRepository;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/technical-advisor")
public class TechnicalAdvisorController {
    @Autowired
    private TechnicalAdvisorRepository technicalAdvisorRepository;

    @GetMapping
    public List<TechnicalAdvisor> getAllTechnicalAdvisors() {
        return technicalAdvisorRepository.findAll();
    }

    @PostMapping
    public TechnicalAdvisor createTechnicalAdvisor(@RequestBody TechnicalAdvisor technicalAdvisor) {
        return technicalAdvisorRepository.save(technicalAdvisor);
    }

}
