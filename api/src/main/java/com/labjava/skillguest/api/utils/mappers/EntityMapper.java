package com.labjava.skillguest.api.utils.mappers;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    @Mapping(target = "jobPosition", source = "jobPosition")
    @Mapping(target = "levelOfExpertise", source = "levelOfExpertise")
    Interview toInterview(InterviewDto interviewDto, @Context JobPositionRepository jobPositionRepository);

    default JobPosition mapJobPosition(String jobPositionName, @Context JobPositionRepository jobPositionRepository) {
        JobPosition jobPosition = jobPositionRepository.findByName(jobPositionName);
        if (jobPosition == null) {
            throw new RuntimeException("JobPosition not found: " + jobPositionName);
        }
        return jobPosition;
    }

    default LevelOfExpertise mapLevelOfExpertise(String levelOfExpertise) {
        for (LevelOfExpertise value : LevelOfExpertise.values()) {
            if (value.getName().equals(levelOfExpertise)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid LevelOfExpertise: " + levelOfExpertise);
    }

    @Mapping(target = "techEmail", source = "jobPosition.name")
    @Mapping(target = "jobPosition", source = "jobPosition.name")
    @Mapping(target = "levelOfExpertise", expression = "java(interview.getLevelOfExpertise().getName())")
    InterviewDto toInterviewDto(Interview interview);


}
