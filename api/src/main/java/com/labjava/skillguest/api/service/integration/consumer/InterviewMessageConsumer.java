package com.labjava.skillguest.api.service.integration.consumer;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.service.interfaces.TechnicalAdvisorService;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.service.interfaces.MessagingService;
import com.labjava.skillguest.api.service.mail.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InterviewMessageConsumer {

    private final InterviewService interviewService;

    private final EmailService emailService;
    private final TechnicalAdvisorService technicalAdvisorService;
    private final MessagingService messagingService;

    final int numberOfAdvisorToNotifyOnce;
    final int urgenceNumberOfAdvisorToNotifyOnce;

    public InterviewMessageConsumer(InterviewService interviewService,
                                    @Qualifier("interviewMessageProducer") MessagingService messagingService,
                                    EmailService emailService, TechnicalAdvisorService technicalAdvisorService,
                                    @Value("${number.advisor.to.notify}")int numberOfAdvisorToNotifyOnce,
                                    @Value("${number.advisor.to.notify.urgent}")int urgenceNumberOfAdvisorToNotifyOnce) {
        this.interviewService = interviewService;
        this.messagingService = messagingService;
        this.emailService = emailService;
        this.technicalAdvisorService = technicalAdvisorService;
        this.numberOfAdvisorToNotifyOnce = numberOfAdvisorToNotifyOnce;
        this.urgenceNumberOfAdvisorToNotifyOnce = urgenceNumberOfAdvisorToNotifyOnce;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.interview.topic}", groupId = "app")
    public void onInterviewFound(Event event) {
        switch (event.getEventType()) {
            case INTERVIEW_CREATE:
            case RELANCE_INTERVIEW:
                onInterviewCreated(event);
                break;
            case ALL_ADVISOR_REFUSE:
                onInterviewRefusedByAllAdvisor(event);
                break;
            default:
                break;
        }

    }



    private void onInterviewCreated(Event event) {
        Interview interview = interviewService.getById(event.getInterviewId());
        int limit  = interview.isUrgent() ? urgenceNumberOfAdvisorToNotifyOnce :numberOfAdvisorToNotifyOnce;
        if (interview != null) {
            List<TechnicalAdvisor> eligibleAdvisors = technicalAdvisorService.findEligibleAdvisors(interview)
                    .stream()
                    .filter(technicalAdvisor -> technicalAdvisor.getTechnicalAdvisorInterviews() == null ||
                            technicalAdvisor.getTechnicalAdvisorInterviews().stream().noneMatch(t -> t.getInterview().equals(interview)))
                    .limit(limit)
                    .collect(Collectors.toList());

            if (!eligibleAdvisors.isEmpty()) {
                for (TechnicalAdvisor advisor : eligibleAdvisors) {
                    if (event.getEventType().equals(Event.Type.RELANCE_INTERVIEW)) {
                        interview.setRequestDate(new Date());
                        interviewService.update(interview);
                    }
                    emailService.send(advisor.getEmail(), interview.getJobPosition().getName(), interview.getDescription());
                }
            } else {
                emailService.send(event.getEmail(), "PAS_DE_REFERENT_DISPONIBLE", interview.getJobPosition().getName());
            }
        }

    }


    private void onInterviewRefusedByAllAdvisor(Event event) {
        Interview interview = interviewService.getById(event.getInterviewId());
        emailService.send(event.getEmail(), "INTERVIEW REFUSE PAR TOUS LES REFERENTS", interview.getJobPosition().getName());
    }



}
