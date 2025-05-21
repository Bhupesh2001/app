package com.moviebooking.app.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LoggingService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "spring-boot-logs";


    public void info(String message) {
        kafkaTemplate.send(TOPIC,message);
    }
}

