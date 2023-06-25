package com.labjava.skillguest.api.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
@ComponentScan({ "com.labjava.skillguest.api.web.controllers" })
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
        super();
    }


    @Override
    //Todo : a quoi sert cette configuration ?
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final Optional<HttpMessageConverter<?>> converterFound = converters.stream()
            .filter(c -> c instanceof AbstractJackson2HttpMessageConverter)
            .findFirst();
        if (converterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) converterFound.get();
            converter.getObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
        }
    }
}
