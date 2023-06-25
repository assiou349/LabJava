package com.labjava.skillguest.api.persistence.repository;

import com.labjava.skillguest.api.persistence.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewRepository  extends JpaRepository<Interview, Long> {
    @Override
    //Todo : Pourquoi tu définis ça, ca ne sert à rien
    Optional<Interview> findById(Long id);
}

