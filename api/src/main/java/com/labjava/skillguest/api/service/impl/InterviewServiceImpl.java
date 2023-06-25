package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.persistence.repository.JobPositionRepository;
import com.labjava.skillguest.api.service.TechnicalAdvisorService;
import com.labjava.skillguest.api.service.interfaces.AbstractService;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.utils.dto.InterviewDto;
import com.labjava.skillguest.api.utils.exception.NotFoundException;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewServiceImpl extends AbstractService<Interview>  implements InterviewService {

    @Autowired
    private TechnicalAdvisorService technicalAdvisorService;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private MessagingService messagingService;


    private final EntityMapper entityMapper;

    public InterviewServiceImpl(EntityMapper entityMapper) {
        super();
        this.entityMapper = entityMapper;
    }


    @Override
    public void processInterview(Interview interview) {
        messagingService.sendMessage("technicalAdvisor-topic",  interview.getId().toString());
    }


    @Override
    protected final InterviewRepository getDao() {
        return interviewRepository;
    }



    @KafkaListener(topics = "technicalAdvisor-topic", groupId = "technicalAdvisor" )
    public void onInterviewFound(@Payload(required = false)String event) throws NotFoundException {

        Interview interview = getDao().findById(Long.parseLong(event)).orElseThrow(()->  new NotFoundException());

            List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview);

            if (!eligibleAdvisors.isEmpty()) {
                for (TechnicalAdvisor advisor : eligibleAdvisors) {
                    //Todo : tu publies toujours uniquement des String
                    //Todo : ton service ne devrait pas savoir que le principe de publication nécessite des topics
                    //Ton système de messaging doit avoir lui cette responsabilité
                    messagingService.sendMessage("notification-topic",   advisor.getId().toString());
                }
            } else {
                //Todo : idem au dessus
                messagingService.sendMessage("notification-topic", interview.getId().toString());
            }
    }
}
