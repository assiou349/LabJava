package com.labjava.skillguest.api;

import com.labjava.skillguest.api.service.mail.EmailService;
import com.labjava.skillguest.api.utils.EmailUtil;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class ApiApplication  implements CommandLineRunner    {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        System.out.println("Hello from Api Application");
    }

    @Override
    public void run(String... args) throws Exception {
        // Exemple d'envoi d'e-mail
        String to = "recipient@example.com";
        String subject = "Test d'e-mail";
        String text = "Ceci est un e-mail de test envoyé depuis Java et Spring Boot.";
        emailService.send("assiou349@gmail.com", subject, text);
        }
}
