package com.labjava.skillguest.api.service;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;

import java.util.List;

//Todo : pourquoi cette interface n'est pas avec celle des autres services
public interface TechnicalAdvisorService {
    List<TechnicalAdvisor> findEligibleAdvisors(Interview interview);
}
