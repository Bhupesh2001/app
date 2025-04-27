package com.moviebooking.app.controller;

import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestParam String loginId,
            @RequestParam String password) {
        return ResponseEntity.ok(userService.loginUser(loginId, password));
    }

    @GetMapping("/{username}/forgot")
    public ResponseEntity<?> resetPassword(
            @PathVariable String username,
            @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.resetPassword(username, newPassword));
    }
}