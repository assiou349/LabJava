package com.labjava.skillguest.api.config;

import com.labjava.skillguest.api.service.integration.EventDeSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
//Todo : tu as configuré pas mal de choses par le code, mais avec Spring Boot ca se fait via les fichiers de config
public class KafkaConsumerConfig {

    @Bean
    public Map<String,Object> conusmerConfig(){
        //Todo : faute de frappe ;)
        Map<String,Object> conusmerConfig = new HashMap<>();
        conusmerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        conusmerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        conusmerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //Todo : interet de mettre un consumerGtoup specifique sous forme de config ?
        conusmerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "technicalAdvisor");


        return conusmerConfig;
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(conusmerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return concurrentKafkaListenerContainerFactory;

    }
}
