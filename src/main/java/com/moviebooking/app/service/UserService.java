package com.moviebooking.app.service;

import com.moviebooking.app.dto.AuthResponseDTO;
import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.entity.User;
import com.moviebooking.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.moviebooking.app.constants.Constants.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    // Registration with uniqueness checks
    public AuthResponseDTO registerUser(UserRegistrationDTO dto) {
        // Check if loginId exists
        if(userRepo.existsByLoginId(dto.getLoginId())) {
            throw new RuntimeException(LOGIN_ID_ALREADY_EXISTS);
        }

        // Check if email exists
        if(userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException(EMAIL_ALREADY_REGISTERED);
        }

        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail())
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .build();
        User response = userRepo.save(user);

        return AuthResponseDTO.builder()
                .firstName(response.getFirstName())
                .lastName(response.getLastName())
                .contactNumber(response.getContactNumber())
                .email(response.getEmail())
                .loginId(response.getLoginId())
                .role("USER")
                .build();
    }

    // Login using loginId
    public AuthResponseDTO loginUser(String loginId, String password) {
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

//        if(passwordEncoder.matches(password, user.getPassword())) {
        if(!password.equals(user.getPassword())) {
            throw new RuntimeException(INVALID_PASSWORD);
        }
        return AuthResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .role(user.getRole())
                .build();
    }

    // Password reset via email
    public AuthResponseDTO resetPassword(String email, String newPassword) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(EMAIL_NOT_REGISTERED));
        user.setPassword(newPassword);
//        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return AuthResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .role(user.getRole())
                .build();
    }
}