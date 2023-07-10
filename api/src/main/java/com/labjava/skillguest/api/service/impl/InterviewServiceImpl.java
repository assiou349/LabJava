package com.labjava.skillguest.api.service.impl;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.JobPosition;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisorInterview;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.service.interfaces.*;
import com.labjava.skillguest.api.persistence.entity.dto.Event;
import com.labjava.skillguest.api.persistence.entity.dto.InterviewDto;
import com.labjava.skillguest.api.service.mail.EmailService;
import com.labjava.skillguest.api.utils.mappers.EntityMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class InterviewServiceImpl extends AbstractService<Interview>  implements InterviewService {

    private final JobPositionService jobPositionService;
    private final InterviewRepository interviewRepository;
    private final MessagingService messagingService;
    private final TechnicalAdvisorService technicalAdvisorService;
    private final EmailService emailService;
    private final TechnicalAdvisorInterviewService technicalAdvisorInterviewService;


    private final int deadLine;
    private final EntityMapper entityMapper;

    public InterviewServiceImpl(EntityMapper entityMapper, JobPositionService jobPositionService,
                                InterviewRepository interviewRepository,
                                @Qualifier("interviewMessageProducer") MessagingService messagingService,
                                TechnicalAdvisorService technicalAdvisorService, EmailService emailService, TechnicalAdvisorInterviewService technicalAdvisorInterviewService,
                                @Value("${relance.deadline}")int deadLine) {
        super();
        this.entityMapper = entityMapper;

        this.jobPositionService = jobPositionService;
        this.interviewRepository = interviewRepository;
        this.messagingService = messagingService;
        this.technicalAdvisorService = technicalAdvisorService;
        this.emailService = emailService;
        this.technicalAdvisorInterviewService = technicalAdvisorInterviewService;
        this.deadLine = deadLine;
    }


    @Scheduled(fixedRateString = "${some.profile.cron}")
    public void checkUnansweredInterviews() {
        processInterviews(this::isInterviewUnanswered);
        processInterviews(this::isInterviewRefusedByAllAdvisor);

    }


    private void processInterviews(Predicate<Interview> interviewPredicate) {
        List<Interview> interviews = findInterviewsByPredicate(interviewPredicate);
        sendRelanceEmails(interviews);
    }

    private List<Interview> findInterviewsByPredicate(Predicate<Interview> interviewPredicate) {
        return interviewRepository.findAll()
                .stream()
                .filter(interviewPredicate)
                .collect(Collectors.toList());
    }


    private boolean isInterviewUnanswered(Interview interview) {
        return null == interview.getTechnicalAdvisorInterviews()
                || interview.getTechnicalAdvisorInterviews().isEmpty()
                || (technicalAdvisorService.findEligibleAdvisors(interview).size() != interview.getTechnicalAdvisorInterviews().size()
                && interview.getTechnicalAdvisorInterviews().stream().noneMatch(technicalAdvisorInterview -> !technicalAdvisorInterview.isRefused()))
                && isDeadLinePassed(interview.getRequestDate());
    }

    private boolean isInterviewRefusedByAllAdvisor(Interview interview) {
        return null != interview.getTechnicalAdvisorInterviews()
                && !interview.getTechnicalAdvisorInterviews().isEmpty()
                && technicalAdvisorService.findEligibleAdvisors(interview).size() == interview.getTechnicalAdvisorInterviews().size()
                && !interview.isClosed();
    }

    private void sendRelanceEmails(List<Interview> interviews) {
        interviews.forEach(this::sendRelanceEmail);
    }

    private void sendRelanceEmail(Interview interview) {
        Event event = new Event();
        if(isInterviewRefusedByAllAdvisor(interview)){
            event.setEventType((Event.Type.ALL_ADVISOR_REFUSE));
        }else {
            event.setEventType((Event.Type.RELANCE_INTERVIEW));
        }
        event.setEmail(interview.getRequesterEmail());
        event.setInterviewId(interview.getId());

        messagingService.sendMessage(event);
    }

    private boolean isDeadLinePassed(Date requestDate) {
        LocalDateTime requestDateTime = requestDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long hoursElapsed = Duration.between(requestDateTime, LocalDateTime.now()).toHours();
        return hoursElapsed >= deadLine;
    }

    @Override
    protected final InterviewRepository getDao() {
        return interviewRepository;
    }

    @Override
    public InterviewDto saveInterview(InterviewDto interviewDto) {
        JobPosition jobPosition = jobPositionService.getByName(interviewDto.getJobPosition());
        if (jobPosition == null) {
            throw new RuntimeException("JobPosition not found: " + interviewDto.getJobPosition());
        }
        Interview interview = entityMapper.toInterview(interviewDto);
        interview.setJobPosition(jobPosition);
        Interview interviewSaved = create(interview);
        interview.setRequestDate(new Date());
        interviewDto.setId(interviewSaved.getId());

        Event event =  new Event();
        event.setEventType((Event.Type.INTERVIEW_CREATE));
        event.setEmail(interview.getRequesterEmail());
        event.setInterviewId(interview.getId());

        messagingService.sendMessage( event);

        return interviewDto;
    }

    @Override
    public InterviewDto updateInterview(InterviewDto interviewDto) {
        return interviewDto;
    }

    @Override
    public Interview getInterviewsNotProcessedFromActor(String actor) {
        return getDao().findAllByRequesterEmailAndTechnicalAdvisorIsNull(actor);
    }

    @Override
    public Interview getById(Long id) {
        return findOne(id);
    }

    @Override
    public void assignInterviewToTechAdvisor(Long interviewId, String email) {
        Interview interview = getById(interviewId);
        if (null == interview.getTechnicalAdvisor()) {
            TechnicalAdvisor advisor = technicalAdvisorService.getByEmail(email);

            TechnicalAdvisorInterview technicalAdvisorInterview = new TechnicalAdvisorInterview();
            technicalAdvisorInterview.setTechnicalAdvisor(advisor);
            technicalAdvisorInterview.setInterview(interview);
            technicalAdvisorInterview.setRefused(true);

            technicalAdvisorInterviewService.save(technicalAdvisorInterview);
            advisor.setActive(false);
            advisor = technicalAdvisorService.updateAdvisor(advisor);
            interview.setTechnicalAdvisor(advisor);
            interview.setClosed(true);
            update(interview);
            emailService.send(advisor.getEmail(), interview.getJobPosition().getName(), "Interview Assignee");
            emailService.send(interview.getRequesterEmail(), "Demande d'assignation d'interview accepte", advisor.getName()+ "  "+advisor.getEmail()+" "+interview.getJobPosition().getName());
        }

    }

    @Override
    public void setInterviewAsRefusedByAdvisor(Long interviewId, String email) {
        Interview interview = getById(interviewId);
        if (null == interview.getTechnicalAdvisor()) {
            TechnicalAdvisor advisor = technicalAdvisorService.getByEmail(email);

            TechnicalAdvisorInterview technicalAdvisorInterview = new TechnicalAdvisorInterview();
            technicalAdvisorInterview.setTechnicalAdvisor(advisor);
            technicalAdvisorInterview.setInterview(interview);
            technicalAdvisorInterview.setRefused(true); // ou

              }
    }
}
