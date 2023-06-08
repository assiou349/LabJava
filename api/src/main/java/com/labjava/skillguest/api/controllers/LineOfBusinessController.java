package com.labjava.skillguest.api.controllers;

import com.labjava.skillguest.api.persistence.entity.LineOfBusiness;
import com.labjava.skillguest.api.persistence.repository.LineOfBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/line-of-business")
public class LineOfBusinessController {
    @Autowired
    private LineOfBusinessRepository lineOfBusinessRepository;

    @GetMapping
    public List<LineOfBusiness> getAllLineOfBusinesses() {
        return lineOfBusinessRepository.findAll();
    }

    @PostMapping
    public LineOfBusiness createLineOfBusiness(@RequestBody LineOfBusiness lineOfBusiness) {
        return lineOfBusinessRepository.save(lineOfBusiness);
    }

}
