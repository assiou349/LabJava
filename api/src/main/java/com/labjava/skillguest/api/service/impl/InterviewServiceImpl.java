package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.InterviewServiceInterface;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.exception.NotFoundException;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InterviewServiceImpl implements InterviewServiceInterface {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Override
    public InterviewDto addInterview(InterviewDto interviewDto) {
        kafkaTemplate.send("interview-topic", new Event(Event.Type.CREATE, null, interviewDto));
        return interviewDto;
    }

   /* @Override
    public InterviewDto getInterviewById(Long id)  throws NotFoundException{
        Interview interview = interviewRepository.findById(id).orElseThrow(()-> new NotFoundException("Interview not found"));
        return entityMapper.toInterviewDto(interview);
    }*/

}
