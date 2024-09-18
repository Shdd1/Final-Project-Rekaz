package com.example.rekazfinalproject.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    private int duration ;

    @Column(columnDefinition = "double not null")
    private double price;


    @Column(columnDefinition = "varchar(20) not null")
    @Enumerated(EnumType.STRING)
    private Subscription.SubscriptionStatus status;

    @Column(columnDefinition = "datetime")
    private LocalDate startDate ;

    @Column(columnDefinition = "datetime")
    private LocalDate endDate ;

    @OneToOne
    @JsonIgnore
    private Owner owner;

    public enum SubscriptionStatus {
        VALID,
        EXPIRED
    }




}
