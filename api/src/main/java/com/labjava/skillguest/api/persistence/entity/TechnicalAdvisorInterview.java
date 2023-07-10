package com.labjava.skillguest.api.persistence.entity;


import com.labjava.skillguest.api.persistence.interfaces.IEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "technical_advisor_interview")
public class TechnicalAdvisorInterview   implements IEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "technical_advisor_id")
    private TechnicalAdvisor technicalAdvisor;

    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    @Column(name = "accepted")
    private boolean refused;

    // getters and setters
}
