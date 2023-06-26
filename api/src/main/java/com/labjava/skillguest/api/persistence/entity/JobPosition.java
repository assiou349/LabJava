package com.labjava.skillguest.api.persistence.entity;


import com.labjava.skillguest.api.persistence.interfaces.IEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class JobPosition  implements IEntity {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_of_business_id")
    private LineOfBusiness parentLineOfBusiness;
}
