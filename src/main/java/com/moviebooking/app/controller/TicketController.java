package com.moviebooking.app.controller;


import com.moviebooking.app.dto.BookTicketDTO;
import com.moviebooking.app.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/{movieName}/add")
    public ResponseEntity<?> bookTickets(
            @PathVariable String movieName,
            @RequestBody BookTicketDTO dto) {
        return ResponseEntity.ok(ticketService.bookTicket(movieName, dto));
    }
}