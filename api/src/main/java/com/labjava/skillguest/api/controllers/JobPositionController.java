package com.labjava.skillguest.api.controllers;

import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.entity.LineOfBusiness;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.persistence.repository.LineOfBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-position")
public class JobPositionController {
    @Autowired
    private JobPositionRepository jobPositionRepository;

    @GetMapping
    public List<JobPosition> getAllJobPositions() {
        return jobPositionRepository.findAll();
    }

    @PostMapping
    public JobPosition createJobPosition(@RequestBody JobPosition jobPosition) {
        return jobPositionRepository.save(jobPosition);
    }

}
