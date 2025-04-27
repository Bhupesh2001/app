package com.moviebooking.app.service;

import com.moviebooking.app.dto.MovieResponseDTO;
import com.moviebooking.app.entity.Movie;
import com.moviebooking.app.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    public List<MovieResponseDTO> getAllMovies() {
        return movieRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MovieResponseDTO> searchMovies(String movieName) {
        return movieRepo.findByMovieNameContainingIgnoreCase(movieName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MovieResponseDTO updateTicketAvailability(String movieName, int tickets) {
        Movie movie = movieRepo.findByMovieName(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setBookedTickets(movie.getBookedTickets() + tickets);
        movie.setTicketStatus(
                (movie.getTotalTickets() - movie.getBookedTickets()) > 0 ?
                        "BOOK ASAP" : "SOLD OUT"
        );
        return convertToDTO(movieRepo.save(movie));
    }

    private MovieResponseDTO convertToDTO(Movie movie) {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setMovieName(movie.getMovieName());
        dto.setTheatreName(movie.getTheatreName());
        dto.setTotalTickets(movie.getTotalTickets());
        dto.setBookedTickets(movie.getBookedTickets());
        dto.setAvailableTickets(movie.getTotalTickets() - movie.getBookedTickets());
        dto.setTicketStatus(movie.getTicketStatus());
        return dto;
    }

    public void deleteMovie(String id) {
        movieRepo.deleteById(id);
    }
}