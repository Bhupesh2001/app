package com.moviebooking.app.controller;

import com.moviebooking.app.dto.MovieResponseDTO;
import com.moviebooking.app.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/all")
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/search/{movieName}")
    public ResponseEntity<List<MovieResponseDTO>> searchMovies(
            @PathVariable String movieName) {
        return ResponseEntity.ok(movieService.searchMovies(movieName));
    }

    @PutMapping("/{movieName}/update/{tickets}")
    public ResponseEntity<?> updateTicketStatus(
            @PathVariable String movieName,
            @PathVariable int tickets) {
        return ResponseEntity.ok(movieService.updateTicketAvailability(movieName, tickets));
    }

    @DeleteMapping("/{movieName}/delete/{id}")
    public ResponseEntity<?> deleteMovie(
            @PathVariable String movieName,
            @PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted");
    }
}