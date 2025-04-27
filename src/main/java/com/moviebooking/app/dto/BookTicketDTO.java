package com.moviebooking.app.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class BookTicketDTO {
    @NotBlank(message = "Movie name is required")
    private String movieName;

    @NotBlank(message = "Theatre name is required")
    private String theatreName;

    @Min(value = 1, message = "At least 1 ticket required")
    private int numberOfTickets;

    @NotEmpty(message = "Seat numbers required")
    private List<String> seatNumbers;

    @NotEmpty(message = "User's LoginId required")
    private String loginId;
}