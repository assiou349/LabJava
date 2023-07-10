package com.labjava.skillguest.api.persistence.entity;


import com.labjava.skillguest.api.persistence.interfaces.IEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
public class JobPosition  implements IEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_of_business_id")
    private LineOfBusiness parentLineOfBusiness;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "superior_position_id")
    private JobPosition superiorPosition;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LineOfBusiness getParentLineOfBusiness() {
        return parentLineOfBusiness;
    }

    public void setParentLineOfBusiness(LineOfBusiness parentLineOfBusiness) {
        this.parentLineOfBusiness = parentLineOfBusiness;
    }

    public JobPosition getSuperiorPosition() {
        return superiorPosition;
    }

    public void setSuperiorPosition(JobPosition superiorPosition) {
        this.superiorPosition = superiorPosition;
    }

    @Override
    public String toString() {
        return "JobPosition{" +
                "parentLineOfBusiness=" + parentLineOfBusiness +
                ", superiorPosition=" + superiorPosition +
                '}';
    }
}
