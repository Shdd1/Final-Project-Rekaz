package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Question should not be null")
    @Column(columnDefinition = "VARCHAR(500) NOT NULL")
    private String question;

    @Column(columnDefinition = "VARCHAR(500)")
    private String answer;

    @ManyToOne
    @JsonIgnore
    private User user ;




}
