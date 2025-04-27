package com.moviebooking.app.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "tickets")
@Data
public class Ticket {
    @Id
    private String id;
    private String movieName;
    private String theatreName;
    private int numberOfTickets;
    private List<String> seatNumbers;
    private Date bookingDate = new Date();
    private String userId;
}