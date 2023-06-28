package com.labjava.skillguest.api.utils.mappers;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.utils.LevelOfExpertise;
import com.labjava.skillguest.api.persistence.entity.dto.InterviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    @Mapping(target = "levelOfExpertise", source = "levelOfExpertise")
    @Mapping(target = "jobPosition", ignore = true)
    Interview toInterview(InterviewDto interviewDtoy);

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
