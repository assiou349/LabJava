package com.labjava.skillguest.api.persistence.entity;


import com.labjava.skillguest.api.utils.LevelOfExpertise;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "technical_advisor_id")
    private TechnicalAdvisor technicalAdvisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_position_id")
    private JobPosition jobPosition;

    @Enumerated(EnumType.STRING)
    private LevelOfExpertise levelOfExpertise;
}
