package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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


public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(100) not null")
    private String Terms;

    @Column(columnDefinition = "datetime")
    private LocalDate startDate ;

    @Column(columnDefinition = "datetime")
    private LocalDate endDate ;

    @Column(columnDefinition = "varchar(20) not null")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @ManyToOne
    @JsonIgnore
    private Investor investor;

    @ManyToOne
    @JsonIgnore
    private Owner owner ;

    @OneToOne
    @JsonIgnore
    private Project project;

    public enum ContractStatus {
        PENDING,
        VALID,
        EXPIRED
    }


}
