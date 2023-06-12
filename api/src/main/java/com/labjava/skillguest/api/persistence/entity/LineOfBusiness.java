package com.labjava.skillguest.api.persistence.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class LineOfBusiness {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

}
