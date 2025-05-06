package com.moviebooking.app.controller;

import com.moviebooking.app.entity.Movie;
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
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/search/{movieName}")
    public ResponseEntity<List<Movie>> searchMovies(
            @PathVariable String movieName) {
        return ResponseEntity.ok(movieService.searchMovies(movieName));
    }
//  admin apne icha se total number of available tickets to reduced kar sakta by X({tickets}) number of tickets
    @PutMapping("/{movieId}/update/{tickets}")
    public ResponseEntity<?> updateTicketStatus(
            @PathVariable String movieId,
            @PathVariable int tickets) {
        return ResponseEntity.ok(movieService.updateTicketAvailability(movieId, tickets));
    }

    @DeleteMapping("/{movieName}/delete/{id}")
    public ResponseEntity<?> deleteMovie(
            @PathVariable String movieName,
            @PathVariable String id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie deleted");
    }
}