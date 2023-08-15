package com.test.testing.payload.request;


import com.test.testing.anotations.PasswordMatches;
import com.test.testing.anotations.ValidEmail;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    private String confirmPassword;


}

