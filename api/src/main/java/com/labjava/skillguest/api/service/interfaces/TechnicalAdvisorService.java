package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;

import java.util.List;

public interface TechnicalAdvisorService {
    List<TechnicalAdvisor> findEligibleAdvisors(Interview interview);

    TechnicalAdvisor getByEmail(String email);

    TechnicalAdvisor getById(Long id);

    TechnicalAdvisor updateAdvisor(TechnicalAdvisor technicalAdvisor);

}
