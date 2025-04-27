package com.moviebooking.app.entity;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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

//    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either USER or ADMIN")
    private String role = "USER";
}