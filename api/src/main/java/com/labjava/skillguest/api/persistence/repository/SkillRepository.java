package com.labjava.skillguest.api.persistence.repository;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.entity.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByJobPositionAndLevelOfExpertiseLessThan(JobPosition jobPosition, LevelOfExpertise levelOfExpertise);
}

