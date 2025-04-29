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
        logger.info("Received ticket update message: {}", message);

        String movieName = extractMovieName(message);
        updateTicketStatus(movieName);
    }

    private String extractMovieName(String message) {
        // Message format: "Tickets booked for: <movieName>"
        return message.substring(message.lastIndexOf(":") + 2);
    }

    private void updateTicketStatus(String movieName) {
        movieRepository.findByMovieName(movieName).ifPresent(movie -> {
            int availableTickets = movie.getTotalTickets() - movie.getBookedTickets();
            String newStatus = availableTickets > 0 ? "BOOK ASAP" : "SOLD OUT";

            if(!movie.getTicketStatus().equals(newStatus)) {
                movie.setTicketStatus(newStatus);
                movieRepository.save(movie);
                logger.info("Updated status for {} to {}", movieName, newStatus);
            }
        });
    }
}