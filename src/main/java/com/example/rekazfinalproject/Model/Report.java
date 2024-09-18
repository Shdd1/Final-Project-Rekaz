package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate createdAt;

    @NotEmpty
    private String content;


    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    @JsonIgnoreProperties("reports")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "investor_id", nullable = false)
    @JsonIgnore
    private Investor investor;


}
