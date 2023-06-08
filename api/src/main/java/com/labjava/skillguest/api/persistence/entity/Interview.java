package com.labjava.skillguest.api.persistence.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private JobPosition jobPosition;

    @ManyToOne
    private TechnicalAdvisor technicalAdvisor;

    @ManyToOne
    private Skill skill;
}
