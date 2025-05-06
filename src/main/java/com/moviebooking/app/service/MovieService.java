package com.moviebooking.app.service;

import com.moviebooking.app.dto.MovieResponseDTO;
import com.moviebooking.app.entity.Movie;
import com.moviebooking.app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.moviebooking.app.constants.Constants.TICKETS_NOT_AVAILABLE;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public List<Movie> searchMovies(String movieName) {
        return movieRepo.findByMovieNameContainingIgnoreCase(movieName);
    }

    public Movie updateTicketAvailability(String movieName, int tickets) {
        Movie movie = movieRepo.findById(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        if(tickets > movie.getTotalTickets()-movie.getBookedTickets())
            throw new RuntimeException(TICKETS_NOT_AVAILABLE);

        movie.setBookedTickets(movie.getBookedTickets() + tickets);
        movie.setTicketStatus(
                (movie.getTotalTickets() - movie.getBookedTickets()) > 0 ?
                        "BOOK ASAP" : "SOLD OUT"
        );
        return movieRepo.save(movie);
    }

    public void deleteMovie(String id) {
        movieRepo.deleteById(id);
    }
}