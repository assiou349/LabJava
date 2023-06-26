package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.entity.JobPosition;

public interface JobPositionService extends IService<JobPosition> {

    JobPosition getByName(String name);
}
