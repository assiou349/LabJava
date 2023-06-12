package com.labjava.skillguest.api.persistence.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;


@Entity
@Data
@Table(name = "Interview")
public class Interview {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "requester_name")
    private String requesterName;

    @Column(name = "requester_email")
    private String requesterEmail;

    @Column(name = "person_to_meet")
    private String personToMeet;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_position_id")
    private JobPosition jobPosition;

    private LevelOfExpertise levelOfExpertise;

    @Column(name = "description")
    private String description;

    @Column(name = "urgent")
    private boolean urgent;
}
