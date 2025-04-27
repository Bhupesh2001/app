package com.moviebooking.app.service;


import com.moviebooking.app.dto.BookTicketDTO;
import com.moviebooking.app.entity.Movie;
import com.moviebooking.app.entity.Ticket;
import com.moviebooking.app.kafka.TicketProducer;
import com.moviebooking.app.repository.MovieRepository;
import com.moviebooking.app.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class TicketService {

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
                .loginId(dto.getLoginId())
                .build();


        // Update movie
        movie.setBookedTickets(movie.getBookedTickets() + dto.getNumberOfTickets());
        movieRepo.save(movie);

        // Save ticket
        ticketRepo.save(ticket);

        // Send Kafka message
        ticketProducer.sendMessage("Ticket booked: " + ticket.getId());

        return "Ticket booked successfully";
    }
}