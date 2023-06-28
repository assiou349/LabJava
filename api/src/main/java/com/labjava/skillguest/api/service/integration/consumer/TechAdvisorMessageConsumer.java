package com.labjava.skillguest.api.service.integration.consumer;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import com.labjava.skillguest.api.service.mail.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TechAdvisorMessageConsumer {

    private final InterviewService interviewService;

    private final MessagingService messagingService;
    private final EmailService emailService;
    private final TechnicalAdvisorService technicalAdvisorService;

    public TechAdvisorMessageConsumer(InterviewService interviewService, @Qualifier("technicalAdvisorMessageProducer") MessagingService messagingService, EmailService emailService, TechnicalAdvisorService technicalAdvisorService) {
        this.interviewService = interviewService;
        this.messagingService = messagingService;
        this.emailService = emailService;
        this.technicalAdvisorService = technicalAdvisorService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.technicalAdvisor.topic}", groupId = "app")
    public void onInterviewFound(Event event) {

        switch (event.getEventType()) {
            case INTERVIEW_ACCEPTED:
                onInterviewAccepted(event);
                break;
            case INTERVIEW_REFUSED:
                onInterviewRefused(event);
                break;
            default:
                break;
        }

    }

    private void onInterviewAccepted(Event event) {
        interviewService.assignInterviewToTechAdvisor(event.getInterviewId(), event.getEmail());
    }

    private void onInterviewRefused(Event event) {
        Interview interview = interviewService.getById(event.getInterviewId());
        Long rejectNumber = interview.getRejectNumber();
        interview.setRejectNumber(rejectNumber++);
        interviewService.update(interview);

    }



}
