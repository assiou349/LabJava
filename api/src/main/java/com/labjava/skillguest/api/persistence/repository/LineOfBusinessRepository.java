package com.labjava.skillguest.api.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.labjava.skillguest.api.persistence.entity.LineOfBusiness;

@Repository
public interface LineOfBusinessRepository extends JpaRepository<LineOfBusiness, Long> {
}
