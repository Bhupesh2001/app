package com.moviebooking.app.kafka;

import com.moviebooking.app.dto.BookTicketDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TicketProducer {
    private static final Logger logger = LoggerFactory.getLogger(TicketProducer.class);
    private static final String TOPIC = "ticket-updates";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TicketProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTicketUpdate(BookTicketDTO dto) {
        String message = String.format("%d tickets booked for: %s (%s) by %s",
                dto.getNumberOfTickets(),
                dto.getMovieName(),
                dto.getTheatreName(),
                dto.getLoginId()
        );

        kafkaTemplate.send(TOPIC, message);
        logger.info("Sent Kafka message: {}", message);
    }
}