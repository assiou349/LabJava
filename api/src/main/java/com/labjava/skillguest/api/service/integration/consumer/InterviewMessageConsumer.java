package com.labjava.skillguest.api.service.integration.consumer;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.service.mail.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InterviewMessageConsumer {

    private final InterviewService interviewService;

    private final EmailService emailService;
    private final TechnicalAdvisorService technicalAdvisorService;
    private final MessagingService messagingService;

    public InterviewMessageConsumer(InterviewService interviewService,
                                    @Qualifier("interviewMessageProducer") MessagingService messagingService, EmailService emailService, TechnicalAdvisorService technicalAdvisorService) {
        this.interviewService = interviewService;
        this.messagingService = messagingService;
        this.emailService = emailService;
        this.technicalAdvisorService = technicalAdvisorService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.interview.topic}", groupId = "app")
    public void onInterviewFound(Event event) {
        switch (event.getEventType()) {
            case INTERVIEW_CREATE:
                onInterviewCreated(event);
                break;
            default:
                break;
        }

    }



    private void onInterviewCreated(Event event) {
        Interview interview = interviewService.getById(event.getInterviewId());
        List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview);

        if (!eligibleAdvisors.isEmpty()) {
            for (TechnicalAdvisor advisor : eligibleAdvisors) {
                emailService.send(advisor.getEmail(), interview.getJobPosition().getName(), interview.getDescription());
            }
        } else {
            emailService.send(event.getEmail(), "PAS_DE_REFERENT_DISPONIBLE", interview.getJobPosition().getName());
        }
    }


}
