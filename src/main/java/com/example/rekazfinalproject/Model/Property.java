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
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//*** All Done by Shahad ****
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Land title cannot be empty")
    @Column(name = "land_title", columnDefinition = "varchar(100) not null")
    private String landTitle;

    @NotEmpty(message = "Location cannot be empty")
    @Column(name = "location", columnDefinition = "varchar(255) not null")
    private String location;

    @NotNull(message = "Land area is required")
    @Column(name = "land_area", columnDefinition = "decimal(10, 2) not null")
    private double landArea; // In square meters or another unit



    @NotEmpty(message = "Project description should be not null")
    @Column(columnDefinition = "varchar(100) not null")
    private String description;


    @ManyToOne
    @JsonIgnore
    private Owner owner;


    //project one to one
    @OneToOne(cascade = CascadeType.ALL , mappedBy = "property")
    private Project project;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "property")
    private Set<Evaluation> evaluations;

}
