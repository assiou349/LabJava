package com.labjava.skillguest.api.service.mail;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.interfaces.INotificationEntity;
import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.service.interfaces.InterviewService;
import com.labjava.skillguest.api.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    SimpleMailMessage simpleMailMessage;


    public void send(String mail ){
        simpleMailMessage.setTo("a.karabou77@gmail.com");
        simpleMailMessage.setSubject("Test Mail");
        simpleMailMessage.setText(mail);
        javaMailSender.send(simpleMailMessage);

    }


    @KafkaListener(topics = "notification-topic", groupId = "technicalAdvisor" )
    public void sendMailOnTechnicalAvisorFound(String event ) throws NotFoundException {
        Interview interview = interviewRepository.findById(Long.parseLong(event)).orElseThrow(()->  new NotFoundException());
       /*     switch (event.getEventType()) {
                case FOUND:
                    simpleMailMessage.setTo(event.getData().getTechEmail());
                    simpleMailMessage.setText(event.getData().getDescription());
                    break;
                case NOTFOUND:
                    simpleMailMessage.setTo(event.getData().getRequesterEmail());
                    simpleMailMessage.setText("NOTFOUND");
                    break;
                default:
                    break;
            }*/
        simpleMailMessage.setText(interview.getDescription());
        simpleMailMessage.setTo(interview.getRequesterEmail());
        simpleMailMessage.setSubject(interview.getJobPosition().getName());
        javaMailSender.send(simpleMailMessage);
        }

}
