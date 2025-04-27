package com.moviebooking.app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
@CompoundIndex(name = "movie_theatre_idx", def = "{'movieName': 1, 'theatreName': 1}", unique = true)
@Data
public class Movie {
    @Id
    private String id;
    private String movieName;
    private String theatreName;
    private int totalTickets;
    private int bookedTickets;
    private String ticketStatus = "BOOK ASAP";

    public Movie(String movieName, String theatreName, int totalTickets) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.totalTickets = totalTickets;
    }
}
