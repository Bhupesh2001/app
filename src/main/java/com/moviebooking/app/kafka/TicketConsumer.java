package com.moviebooking.app.kafka;

import com.moviebooking.app.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TicketConsumer.class);
    private final MovieRepository movieRepository;

    @KafkaListener(topics = "ticket-updates", groupId = "movie-group")
    public void consumeTicketUpdate(String message) {
//        Whenever a new ticket is booked, this consumer catches it and logs it
//        Later can be used to send this log to centralized log repo
        logger.info("Received ticket update message: {}", message);
    }
}