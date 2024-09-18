package com.example.rekazfinalproject.DTO;

import com.example.rekazfinalproject.Model.Project;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PropertyDTO {

    @NotEmpty(message = "Land title cannot be empty")
    private String landTitle;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotNull(message = "Land area is required")
    private double landArea; // In square meters or another unit

    @NotEmpty(message = "Project description should be not null")
    private String description;







}
