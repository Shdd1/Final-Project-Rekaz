package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Entity
@Setter
@Getter
    //*** All Done by shahad ****
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message ="score can not be null" )
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must be at most 5")
    private int score;

    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "comment can not be empty")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "investor_id")
    @JsonIgnore
    private Investor investor;



    @ManyToOne
    @JsonIgnore
    private Owner owner;



}
