package com.labjava.skillguest.api.persistence.repository;

import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByJobPositionIn(List<JobPosition> jobPositionList);
    List<Skill> findAllByLevelOfExpertiseGreaterThanEqual( LevelOfExpertise levelOfExpertise);
}

