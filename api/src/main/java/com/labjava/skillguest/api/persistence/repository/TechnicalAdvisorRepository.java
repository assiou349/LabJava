package com.labjava.skillguest.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;

import java.util.Optional;

@Repository
public interface TechnicalAdvisorRepository extends JpaRepository<TechnicalAdvisor, Long> {

    TechnicalAdvisor findByEmail(String mail);
}
