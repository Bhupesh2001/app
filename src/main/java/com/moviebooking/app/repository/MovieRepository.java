package com.moviebooking.app.repository;


import com.moviebooking.app.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    Optional<Movie> findByMovieNameAndTheatreName(String movieName, String theatreName);

    List<Movie> findByMovieNameContainingIgnoreCase(String movieName);

}