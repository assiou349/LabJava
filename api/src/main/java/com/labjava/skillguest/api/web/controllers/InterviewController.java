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
public class InterviewController extends AbstractController<Interview> {
    @Autowired
    private InterviewService interviewService;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    private final EntityMapper entityMapper;

    public InterviewController(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Interview createInterview(@RequestBody InterviewDto interviewDto) {
        Interview interview = entityMapper.toInterview(interviewDto, jobPositionRepository);
        Interview interviewSaved = createInternal(interview);
        interviewService.processInterview(interviewSaved);
        return interviewSaved;
    }

    @Override
    protected IOperations<Interview> getService() {
        return interviewService;
    }
}
