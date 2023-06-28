package com.labjava.skillguest.api.persistence.entity.dto;

import lombok.Data;

@Data
public class JobPositionDto {

    private String name;
    private Long lineOfBusinessId;
    private Long jobPositionId;
}
