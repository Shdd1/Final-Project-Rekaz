package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//*** All Done by Shahad ****
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(columnDefinition = "datetime not null")
    private LocalDateTime consultationDate;

    @NotNull(message = "Duration should not be null!")
    @Column(columnDefinition = "DOUBLE not null")
    private double duration;

    //consultation fee=60;
    @Column(columnDefinition = "DOUBLE not null")
    private double consultationFee;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;



    //Many consultation to one Investor
    @ManyToOne
    @JsonIgnore
    private Investor investor;


    //Many consultation to one Owner
    @ManyToOne
    @JsonIgnore
    private Owner owner;

    public enum ConsultationStatus {
        IN_PROGRESS,
        CANCELED
    }


}

