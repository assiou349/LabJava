package com.labjava.skillguest.api.service.mail;

import com.labjava.skillguest.api.persistence.entity.Interview;
import com.labjava.skillguest.api.persistence.entity.TechnicalAdvisor;
import com.labjava.skillguest.api.service.integration.Event;
import com.labjava.skillguest.api.utils.dto.InterviewTechAdvisorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    SimpleMailMessage simpleMailMessage;


    public void send(String mail ){
        simpleMailMessage.setTo("a.karabou77@gmail.com");
        simpleMailMessage.setSubject("Test Mail");
        simpleMailMessage.setText(mail);
        javaMailSender.send(simpleMailMessage);

    }


    @KafkaListener(topics = "technicalAdvisor-topic", groupId = "technicalAdvisor" )
    public void sendMailOnUserChange(Event<Long, InterviewTechAdvisorDto> event ){
            switch (event.getEventType()) {
                case FOUND:
                    simpleMailMessage.setTo(event.getData().getEmail());
                    simpleMailMessage.setText(event.getData().getDescription());
                    break;
                case NOTFOUND:
                    simpleMailMessage.setTo(event.getData().getRequesterEmail());
                    simpleMailMessage.setText("NOTFOUND");
                    break;
                default:
                    break;
            }

        simpleMailMessage.setSubject(event.getData().getJobPosition());
        javaMailSender.send(simpleMailMessage);
        }

}
