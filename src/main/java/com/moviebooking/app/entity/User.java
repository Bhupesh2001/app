package com.moviebooking.app.entity;

import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document(collection = "users")
@Data
@Builder
public class User {
    @Id
    private String loginId; // Now serves as the primary key

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String contactNumber;

   @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either USER or ADMIN")
   @Builder.Default
    private String role = "USER";
}