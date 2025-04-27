package com.moviebooking.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequest {
    @NotNull
    @NotBlank(message = "loginId is required")
    private String loginId;

    @NotNull
    @NotBlank(message = "password is required")
    private String password;
}
