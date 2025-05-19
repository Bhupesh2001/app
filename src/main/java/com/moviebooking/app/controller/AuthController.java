package com.moviebooking.app.controller;

import com.moviebooking.app.dto.AuthResponseDTO;
import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.service.LoggingService;
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

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LoggingService logger;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO dto) {
        logger.logInfo("register wala controller call hua hae");
        log.info("[Invoked /register endpoint], data :{}",dto);
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(
            @RequestParam String loginId,
            @RequestParam String password) {
        logger.logInfo("login wala controller call hua hae");
        log.info("[Invoked /login endpoint], loginId:{}, password:{}",loginId,password);
        return ResponseEntity.ok(userService.loginUser(loginId, password));
    }
//    loginId == username
    @GetMapping("/{username}/forgot")
    public ResponseEntity<AuthResponseDTO> resetPassword(
            @PathVariable String username,
            @RequestParam String newPassword) {
        log.info("[Invoked /forgot endpoint], username:{}, newPassword:{}",username,newPassword);
        return ResponseEntity.ok(userService.resetPassword(username, newPassword));
    }
}