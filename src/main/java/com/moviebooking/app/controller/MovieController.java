package com.moviebooking.app.controller;

import com.moviebooking.app.entity.Movie;
import com.moviebooking.app.kafka.LoggingService;
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

    @Autowired
    private LoggingService logger;

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        logger.info("[Invoked getAllMovies]");
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/search/{movieName}")
    public ResponseEntity<List<Movie>> searchMovies(
            @PathVariable String movieName) {
        logger.info("[Invoked searchMovie]");
        return ResponseEntity.ok(movieService.searchMovies(movieName));
    }

    @PutMapping("/{movieId}/update/{tickets}")
    public ResponseEntity<?> updateTicketStatus(
            @PathVariable String movieId,
            @PathVariable int tickets) {
        logger.info("[Invoked updateTicketStatus]");
        return ResponseEntity.ok(movieService.updateTicketAvailability(movieId, tickets));
    }

    @DeleteMapping("/{movieName}/delete/{id}")
    public ResponseEntity<?> deleteMovie(
            @PathVariable String movieName,
            @PathVariable String id) {
        logger.info("[Invoked deleteMovies]");
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted");
    }
}