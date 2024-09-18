package com.example.rekazfinalproject.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SubscriptionDTO {

    private int ownerId;

    @NotNull(message = "Subscription duration should be not empty ")
    private int duration ;

}
