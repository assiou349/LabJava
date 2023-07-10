package com.labjava.skillguest.api.persistence.entity;


import com.labjava.skillguest.api.persistence.interfaces.IEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class TechnicalAdvisor implements IEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private boolean active;

    @OneToMany(mappedBy = "technicalAdvisor")
    List<Skill> skills;

    @OneToMany(mappedBy = "technicalAdvisor")
    private List<TechnicalAdvisorInterview> technicalAdvisorInterviews;

}
