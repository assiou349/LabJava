package com.labjava.skillguest.api.service.integration;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.TechnicalAdvisorService;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.dto.InterviewTechAdvisorDto;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

public class InterviewProcessor {

    @Autowired
    private TechnicalAdvisorService technicalAdvisorService;

    @Autowired
    KafkaTemplate kafkaTemplate;

    private final EntityMapper entityMapper;

    public InterviewProcessor(EntityMapper entityMapper, InterviewRepository interviewRepository) {
        this.entityMapper = entityMapper;
        this.interviewRepository = interviewRepository;
    }

    @Autowired
    private JobPositionRepository jobPositionRepository;
    private final InterviewRepository interviewRepository;


    @KafkaListener(topics = "interview-topic", groupId = "interview" )
    public void processInterview(Event<Long, InterviewDto> event) {

        Interview interview = entityMapper.toInterview(event.getData(), jobPositionRepository);

        List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview);

        InterviewTechAdvisorDto dto = new InterviewTechAdvisorDto();

        if (!eligibleAdvisors.isEmpty()) {
            interviewRepository.save(interview);
            for (TechnicalAdvisor advisor : eligibleAdvisors) {
                dto.setEmail(advisor.getEmail());
                dto.setDescription(interview.getDescription());
                dto.setJobPosition(interview.getJobPosition().getName());
                kafkaTemplate.send("technicalAdvisor-topic", new Event(Event.Type.FOUND, advisor.getId(), dto));
            }
        } else {
            dto.setRequesterEmail(interview.getRequesterEmail());
            kafkaTemplate.send("technicalAdvisor-topic", new Event(Event.Type.NOTFOUND, null, dto));
        }
    }
}
