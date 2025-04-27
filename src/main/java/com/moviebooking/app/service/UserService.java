package com.moviebooking.app.service;

import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.entity.User;
import com.moviebooking.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    // Registration with uniqueness checks
    public String registerUser(UserRegistrationDTO dto) {
        // Check if loginId exists
        if(userRepo.existsByLoginId(dto.getLoginId())) {
            throw new RuntimeException("Login ID already exists!");
        }

        // Check if email exists
        if(userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail())
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .build();
        userRepo.save(user);
        return "Registration successful!";
    }

    // Login using loginId
    public String loginUser(String loginId, String password) {
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if(passwordEncoder.matches(password, user.getPassword())) {
        if(password.equals(user.getPassword())) {
            return "Login successful";
        }
        throw new RuntimeException("Invalid password");
    }

    // Password reset via email
    public String resetPassword(String email, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not registered"));
        user.setPassword(newPassword);
//        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return "Password updated!";
    }
}