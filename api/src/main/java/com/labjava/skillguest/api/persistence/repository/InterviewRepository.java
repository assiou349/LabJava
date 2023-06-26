package com.labjava.skillguest.api.persistence.repository;

import com.labjava.skillguest.api.persistence.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository  extends JpaRepository<Interview, Long> {
    @Override
    Optional<Interview> findById(Long id);

    Interview findAllByRequesterEmailAndTechnicalAdvisorIsNull(String actor);
}

