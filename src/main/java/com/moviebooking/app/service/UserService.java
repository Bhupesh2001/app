package com.moviebooking.app.service;

import com.moviebooking.app.dto.AuthResponseDTO;
import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.entity.User;
import com.moviebooking.app.kafka.LoggingService;
import com.moviebooking.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.moviebooking.app.constants.Constants.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LoggingService log;

    public AuthResponseDTO registerUser(UserRegistrationDTO dto) {
        log.info("[Invoked registerUser: Service]");

        if(userRepo.existsByLoginId(dto.getLoginId())) {
            log.info("[Invoked registerUser: Service]");
            throw new RuntimeException(LOGIN_ID_ALREADY_EXISTS);
        }


        if(userRepo.existsByEmail(dto.getEmail())) {
            log.info("[Invoked registerUser: Service]");
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

        log.info("[registerUser: Service]");
        return AuthResponseDTO.builder()
                .firstName(response.getFirstName())
                .lastName(response.getLastName())
                .contactNumber(response.getContactNumber())
                .email(response.getEmail())
                .loginId(response.getLoginId())
                .role("USER")
                .build();
    }


    public AuthResponseDTO loginUser(String loginId, String password) {

        log.info("[Invoked loginUser: Service]");
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> {
                    log.info("[Invoked loginUser: Service]");
                    return new RuntimeException(USER_NOT_FOUND);
                });

        if(!password.equals(user.getPassword())) {
            log.info("[Invoked loginUser: Service]");
            throw new RuntimeException(INVALID_PASSWORD);
        }

        log.info("[loginUser: Service]");
        return AuthResponseDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .loginId(user.getLoginId())
                .role(user.getRole())
                .build();
    }


    public AuthResponseDTO resetPassword(String loginId, String newPassword) {
        log.info("[Invoked resetPassword: Service]");
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> {
                    log.info("[Invoked resetPassword: Service]");
                    return new RuntimeException(EMAIL_NOT_REGISTERED);
                });
        user.setPassword(newPassword);
        userRepo.save(user);
        log.info("[resetPassword: Service]");
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