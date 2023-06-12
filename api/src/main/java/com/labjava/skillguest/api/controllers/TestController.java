package com.labjava.skillguest.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${message.hello}")
    private String message;

   /* @Autowired
    private EmailService emailService;*/

    @RequestMapping("Hello")
    String hello() {
       // EmailService.sendEmail("a.karabou77@gmail.com");
        return message;
    }
}
