package com.example.rekazfinalproject.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor


public class ContractDTO {



    @NotEmpty(message = "Terms should be not null")
    private String Terms;

    @NotNull(message = "Start Date should be not null")
    private LocalDate startDate ;

    @NotNull(message = "End Date should be not null")
    private LocalDate endDate ;

}
