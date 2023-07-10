package com.labjava.skillguest.api.service.interfaces;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisorInterview;
import com.labjava.skillguest.api.persistence.entity.dto.InterviewDto;

import java.util.List;

public interface TechnicalAdvisorInterviewService {
    TechnicalAdvisorInterview save(TechnicalAdvisorInterview technicalAdvisorInterview);

    void askInterviewAssignment(Long techAdvisorId, Long interviewID, boolean accepted);
}
