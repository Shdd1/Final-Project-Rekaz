package com.example.rekazfinalproject.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OwnerDTO {

    //*** Done by Danah ****
    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    private boolean isActive;

    @NotNull(message = "Commercial Register is required")
    private int commercialRegister;

    @NotEmpty(message = "Scope of Work is required")
    private String scopeOfWork;

    @PositiveOrZero(message = "Number of projects must be zero or positive")
    private int numOfProject;
}
