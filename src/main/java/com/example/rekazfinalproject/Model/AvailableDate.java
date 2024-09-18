package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "datetime not null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent
    private LocalDateTime date ;

    @Column(columnDefinition = "boolean")
    private Boolean booked = false ;



    @ManyToOne
    @JsonIgnore
    private Investor investor;


}
