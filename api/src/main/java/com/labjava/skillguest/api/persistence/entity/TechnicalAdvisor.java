package com.labjava.skillguest.api.persistence.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TechnicalAdvisor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private LineOfBusiness lineOfBusiness;

    @ManyToOne
    private LevelOfExpertise levelOfExpertise;



    // Constructors, getters, setters, etc.
}
