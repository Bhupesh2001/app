package com.moviebooking.app.dto;

import lombok.Data;

@Data
public class MovieResponseDTO {
    private String movieName;
    private String theatreName;
    private int totalTickets;
    private int bookedTickets;
    private int availableTickets;
    private String ticketStatus;
}