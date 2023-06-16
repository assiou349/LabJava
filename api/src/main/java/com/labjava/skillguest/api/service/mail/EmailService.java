package com.labjava.skillguest.api.service.mail;

import com.labjava.skillguest.api.persistence.interfaces.INotificationEntity;
import com.labjava.skillguest.api.service.integration.Event;
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
    SimpleMailMessage simpleMailMessage;


    public void send(String mail ){
        simpleMailMessage.setTo("a.karabou77@gmail.com");
        simpleMailMessage.setSubject("Test Mail");
        simpleMailMessage.setText(mail);
        javaMailSender.send(simpleMailMessage);

    }


    @KafkaListener(topics = "technicalAdvisor-topic", groupId = "technicalAdvisor" )
    public void sendMailOnUserChange(Event<Long, INotificationEntity> event ){
            switch (event.getEventType()) {
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
            }

        simpleMailMessage.setSubject(event.getData().getSubjet());
        javaMailSender.send(simpleMailMessage);
        }

}
