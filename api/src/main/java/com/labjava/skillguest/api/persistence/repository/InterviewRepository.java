package com.labjava.skillguest.api.persistence.repository;

import com.labjava.skillguest.api.persistence.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository  extends JpaRepository<Interview, Long> {

    Interview findAllByRequesterEmailAndTechnicalAdvisorIsNull(String actor);
    List<Interview> findAllByRequestDateIsLessThanEqual(Date date);

    List<Interview> findAllByTechnicalAdvisorInterviewsIsNotNullAndRequestDateIsLessThanAndClosedIsFalse(Date date);
}

