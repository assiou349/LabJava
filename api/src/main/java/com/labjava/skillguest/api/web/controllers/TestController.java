package com.labjava.skillguest.api.web.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${message.hello}")
    private String message;

    @RequestMapping("Hello")
    String hello() {
        return message;
    }
}
