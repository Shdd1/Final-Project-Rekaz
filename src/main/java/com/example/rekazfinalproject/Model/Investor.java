package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Investor {

    //*** All Done by Danah ****

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Commercial Register must not be null")
    @Positive(message = "Commercial Register must be a positive number")
    @Column(columnDefinition ="int not null")
    private int commercialRegister;

    @PositiveOrZero(message = "Number of Investments must be zero or a positive number")
    @Column(columnDefinition = "int")
    private int numOfInvest;

    @NotEmpty(message = "Investor Sectors must not be blank")
    @Size(min = 3, max = 100, message = "Investor Sectors must be between 3 and 100 characters")
    @Column(columnDefinition = "varchar(100) not null")
    private String investorSectors;

    @PastOrPresent(message = "Creation date must be in the past or today")
    private LocalDate createdAt;

    @Column(columnDefinition = "double")
    private double rate ;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;



    @OneToMany(cascade = CascadeType.ALL , mappedBy = "investor")
    private Set<Project> projects;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<Bid> bids;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<Contract> contracts;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<Rating> ratings;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<Complaint> complaints;

    @OneToMany(mappedBy = "investor", cascade = CascadeType.ALL)
    private List<Report> reports;

    @OneToMany(mappedBy = "investor")
    private List<Evaluation> evaluations;




//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
//    private Set<Cons> consultings;
}
