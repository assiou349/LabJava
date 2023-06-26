package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.JobPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class JobPositionServiceImpl  extends AbstractService<JobPosition> implements JobPositionService {
    private final JobPositionRepository jobPositionRepository;

    public JobPositionServiceImpl(JobPositionRepository jobPositionRepository) {
        this.jobPositionRepository = jobPositionRepository;
    }

    @Override
    protected JpaRepository<JobPosition, Long> getDao() {
        return jobPositionRepository;
    }

    @Override
    public JobPosition getByName(String name) {
        return jobPositionRepository.findByName(name);
    }
}
