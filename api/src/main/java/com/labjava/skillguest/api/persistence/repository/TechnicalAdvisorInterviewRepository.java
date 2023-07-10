package com.labjava.skillguest.api.persistence.repository;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisorInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalAdvisorInterviewRepository extends JpaRepository<TechnicalAdvisorInterview, Long> {

}

