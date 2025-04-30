package com.moviebooking.app.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebooking.app.dto.BookTicketDTO;
import com.moviebooking.app.entity.Movie;
import com.moviebooking.app.entity.Ticket;
import com.moviebooking.app.kafka.TicketProducer;
import com.moviebooking.app.repository.MovieRepository;
import com.moviebooking.app.repository.TicketRepository;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private TicketProducer ticketProducer;

    public String bookTicket(String movieName, BookTicketDTO dto) {
        // Check movie availability
        Movie movie = movieRepo.findByMovieNameAndTheatreName(movieName, dto.getTheatreName())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if((movie.getTotalTickets() - movie.getBookedTickets()) < dto.getNumberOfTickets()) {
            throw new RuntimeException("Not enough tickets available");
        }

        // Create ticket
        Ticket ticket = Ticket.builder()
                .movieName(movieName)
                .theatreName(dto.getTheatreName())
                .numberOfTickets(dto.getNumberOfTickets())
                .seatNumbers(dto.getSeatNumbers())
                .loginId(dto.getLoginId())  // it's the username/loginId of the user who booked the tickets
                .build();


        // Update movie
        movie.setBookedTickets(movie.getBookedTickets() + dto.getNumberOfTickets());
        movieRepo.save(movie);

        // Save ticket
        ticketRepo.save(ticket);

        // Send Kafka message
        ticketProducer.sendTicketUpdate(dto);

        return "Ticket booked successfully";
    }
}