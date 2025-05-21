package com.moviebooking.app.controller;

import com.moviebooking.app.dto.AuthResponseDTO;
import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.kafka.LoggingService;
import com.moviebooking.app.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoggingService logger;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        logger.info("[Invoked /register endpoint]");
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(
            @RequestParam String loginId,
            @RequestParam String password) {
        logger.info("[Invoked /login endpoint]");
        return ResponseEntity.ok(userService.loginUser(loginId, password));
    }
//    loginId == username
    @GetMapping("/{username}/forgot")
    public ResponseEntity<AuthResponseDTO> resetPassword(
            @PathVariable String username,
            @RequestParam String newPassword) {
        logger.info("[Invoked /forgot endpoint]");
        return ResponseEntity.ok(userService.resetPassword(username, newPassword));
    }
}