package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.TechnicalAdvisorService;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewServiceImpl extends AbstractService<Interview>  implements InterviewService {

    @Autowired
    private TechnicalAdvisorService technicalAdvisorService;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    KafkaTemplate kafkaTemplate;

    private final EntityMapper entityMapper;

    public InterviewServiceImpl(EntityMapper entityMapper) {
        super();
        this.entityMapper = entityMapper;
    }


    @Override
    public InterviewDto addInterview(InterviewDto interviewDto) {
        kafkaTemplate.send("technicalAdvisor-topic", new Event(Event.Type.CREATE, null, interviewDto));
        return interviewDto;
    }


    @Override
    protected final InterviewRepository getDao() {
        return interviewRepository;
    }

    @Override
    protected JpaSpecificationExecutor<Interview> getSpecificationExecutor() {
        return null;
    }



    @KafkaListener(topics = "technicalAdvisor-topic", groupId = "technicalAdvisor" )
    public void processInterview(Event<Long, InterviewDto> event) {

        Interview interview = entityMapper.toInterview(event.getData(), jobPositionRepository);

        List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview);


        if (!eligibleAdvisors.isEmpty()) {
            for (TechnicalAdvisor advisor : eligibleAdvisors) {
                event.getData().setTechEmail(advisor.getEmail());
                kafkaTemplate.send("technicalAdvisor-topic", new Event(Event.Type.FOUND, advisor.getId(), event.getData()));
            }
        } else {
            kafkaTemplate.send("technicalAdvisor-topic", new Event(Event.Type.NOTFOUND, null, event.getData()));
        }
    }
}
