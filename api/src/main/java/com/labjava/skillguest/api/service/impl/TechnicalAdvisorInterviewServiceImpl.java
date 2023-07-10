package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisorInterview;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.TechnicalAdvisorInterviewRepository;
import com.labjava.skillguest.api.service.interfaces.*;
import com.labjava.skillguest.api.service.mail.EmailService;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class TechnicalAdvisorInterviewServiceImpl extends AbstractService<TechnicalAdvisorInterview>  implements TechnicalAdvisorInterviewService {

    private final TechnicalAdvisorInterviewRepository advisorInterviewRepository;

    public TechnicalAdvisorInterviewServiceImpl( TechnicalAdvisorInterviewRepository advisorInterviewRepository) {
        super();
        this.advisorInterviewRepository = advisorInterviewRepository;
    }


    @Override
    protected JpaRepository<TechnicalAdvisorInterview, Long> getDao() {
        return advisorInterviewRepository;
    }

    @Override
    public TechnicalAdvisorInterview save(TechnicalAdvisorInterview technicalAdvisorInterview) {
        return save(technicalAdvisorInterview);
    }
}
