package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Project description should be not null")
    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @NotNull(message = "Project deadline should be not null")
    @Column(columnDefinition = "datetime")
    private LocalDate deadline;

    @NotNull(message = "Project budget should be not null")
    @Column(columnDefinition = "double not null")
    private double budget;

    @Column(columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private BidStatus status;

        
    @Column(columnDefinition = "varchar(500)")
    private String comment ;

    
    @ManyToOne
    @JsonIgnore
    private Project project ;


    @ManyToOne
    @JsonIgnore
    private Investor investor;


    public enum BidStatus {
        PENDING,
        APPROVED,
        REJECTED
    }










}
