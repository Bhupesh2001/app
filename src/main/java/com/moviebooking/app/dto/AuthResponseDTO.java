package com.moviebooking.app.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponseDTO {
    private String loginId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String role;
}
