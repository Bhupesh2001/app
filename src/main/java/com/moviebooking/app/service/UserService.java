package com.moviebooking.app.service;

import com.moviebooking.app.dto.AuthResponseDTO;
import com.moviebooking.app.dto.UserRegistrationDTO;
import com.moviebooking.app.entity.User;
import com.moviebooking.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.moviebooking.app.constants.Constants.*;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepo;

//    @Autowired
//    private PasswordEncoder passwordEncoder;


    public AuthResponseDTO registerUser(UserRegistrationDTO dto) {
        log.info("[Invoked registerUser: Service]");
        // Check if loginId exists
        if(userRepo.existsByLoginId(dto.getLoginId())) {
            log.error("[Invoked registerUser: Service], {}",LOGIN_ID_ALREADY_EXISTS);
            throw new RuntimeException(LOGIN_ID_ALREADY_EXISTS);
        }

        // Check if email exists
        if(userRepo.existsByEmail(dto.getEmail())) {
            log.error("[Invoked registerUser: Service], {}",EMAIL_ALREADY_REGISTERED);
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

        log.info("[registerUser: Service], User saved to DB : {}",response);
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

        log.info("[Invoked loginUser: Service], loginId:{}, password:{}",loginId,password);
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> {
                    log.error("[Invoked loginUser: Service], Invalid Credentials: {}",USER_NOT_FOUND);
                    return new RuntimeException(USER_NOT_FOUND);
                });

//        if(passwordEncoder.matches(password, user.getPassword())) {
        if(!password.equals(user.getPassword())) {
            log.error("[Invoked loginUser: Service], Invalid Credentials: {}",INVALID_PASSWORD);
            throw new RuntimeException(INVALID_PASSWORD);
        }

        log.info("[loginUser: Service], User: {}",user);
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
    public AuthResponseDTO resetPassword(String loginId, String newPassword) {
        log.info("[Invoked resetPassword: Service], loginId:{}, password:{}",loginId,newPassword);
        User user = userRepo.findByLoginId(loginId)
                .orElseThrow(() -> {
                    log.error("[Invoked resetPassword: Service], Invalid Email: {}",EMAIL_NOT_REGISTERED);
                    return new RuntimeException(EMAIL_NOT_REGISTERED);
                });
        user.setPassword(newPassword);
//        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        log.info("[resetPassword: Service], password updated:");
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