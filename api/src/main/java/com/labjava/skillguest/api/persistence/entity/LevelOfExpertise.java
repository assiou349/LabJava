package com.labjava.skillguest.api.persistence.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LevelOfExpertise {
    @Id
    private String code;

    private String name;
}
