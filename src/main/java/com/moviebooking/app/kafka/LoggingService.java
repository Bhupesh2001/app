package com.moviebooking.app.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "spring-boot-logs";


    public void info(String message) {
        log.info(message);
//        kafkaTemplate.send(TOPIC,message);
    }
}

