package com.labjava.skillguest.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.labjava.skillguest.api.persistence.entity.JobPosition;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
}
