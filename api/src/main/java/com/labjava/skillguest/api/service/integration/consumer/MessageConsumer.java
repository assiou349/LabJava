package com.labjava.skillguest.api.service.integration.consumer;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.service.TechnicalAdvisorService;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.service.mail.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageConsumer {

    private final InterviewService interviewService;

    private final MessagingService messagingService;
    private final EmailService emailService;
    private final TechnicalAdvisorService technicalAdvisorService;

    public MessageConsumer(InterviewService interviewService, MessagingService messagingService, EmailService emailService, TechnicalAdvisorService technicalAdvisorService) {
        this.interviewService = interviewService;
        this.messagingService = messagingService;
        this.emailService = emailService;
        this.technicalAdvisorService = technicalAdvisorService;
    }


    @KafkaListener(topics = "${kafka.topics}", groupId = "app")
    public void onInterviewFound(Event event) {

        switch (event.getEventType()) {
            case INTERVIEW_CREATE:
                onInterviewCreated(event);
                break;
            case ELIGIBLE_TECH_FOUND:
                onEligibleTechFound(event);
                break;
            case NO_TECH_AVAILABLE:
                onEligibleTechNotFound(event);
                break;
            default:
                break;
        }

    }

    private void onEligibleTechFound(Event event) {
        Interview interview = interviewService.getInterviewsNotProcessedFromActor(event.getActor());
        List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview);
        for (TechnicalAdvisor advisor : eligibleAdvisors) {
            emailService.send(advisor.getEmail(), interview.getJobPosition().getName(), interview.getDescription());
        }
    }

    private void onEligibleTechNotFound(Event event) {
        emailService.send(event.getActor(), "PAS_DE_REFERENT_DISPONIBLE", event.getData());

    }

    private void onInterviewCreated(Event event) {
        Interview interview = interviewService.getInterviewsNotProcessedFromActor(event.getActor());
        List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview);

        if (!eligibleAdvisors.isEmpty()) {
            event.setEventType((Event.Type.ELIGIBLE_TECH_FOUND));

        } else {
            event.setEventType((Event.Type.NO_TECH_AVAILABLE));
        }
        messagingService.sendMessage(event);
    }


}
