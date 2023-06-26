package com.labjava.skillguest.api.service.mail;

import com.labjava.skillguest.api.persistence.repository.InterviewRepository;
import com.labjava.skillguest.api.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class EmailService {


    final String fromEmail ; //requires valid gmail id

    final String password ; // correct password for gmail id

    @Autowired
    private InterviewRepository interviewRepository;

    public EmailService( @Value("${spring.mail.username}") final String fromEmail,
                         @Value("${spring.mail.password}") final String password) {
        this.fromEmail = fromEmail;
        this.password = password;
    }

    public void send(String toMail, String subject, String text ){

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = getAuthenticator();
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toMail,subject, text);


    }

    private Authenticator getAuthenticator() {
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        return auth;
    }



}
