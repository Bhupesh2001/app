package com.moviebooking.app.repository;


import com.moviebooking.app.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    // Find by exact movie name and theatre name (composite key)
    Optional<Movie> findByMovieNameAndTheatreName(String movieName, String theatreName);

    // Search by partial movie name (case-insensitive)
    List<Movie> findByMovieNameContainingIgnoreCase(String movieName);

    // Find all movies in a specific theatre
    List<Movie> findByTheatreName(String theatreName);

    Optional<Movie> findByMovieName(String movieName);
}