package com.moviebooking.app.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketProducer {

    private static final String TOPIC = "ticket-updates";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TicketProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}