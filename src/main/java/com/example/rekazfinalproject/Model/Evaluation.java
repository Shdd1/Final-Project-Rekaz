package com.example.rekazfinalproject.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String projectType;
    private Double minBudget;
    private Double maxBudget;
    private String description;
    private String recommendation;

    @Enumerated(EnumType.STRING)
    private Status status;

    private double price = 99;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "investor_id")
    private Investor investor;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private Owner owner;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "property_id")
    private Property property;


    public enum Status {
        IN_PROGRESS,
        COMPLETED
    }

}
