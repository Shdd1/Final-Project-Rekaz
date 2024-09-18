package com.example.rekazfinalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
    //*** All Done by shahad ****
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "Type cannot be empty")
    private String type;



    @Column(name = "description", length = 500, nullable = false)
    @NotEmpty(message = "Description cannot be empty")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Column(name = "file", nullable = false)
    @NotEmpty(message = "File path cannot be blank")
    @Pattern(regexp = "^(.*\\.(?i)(jpg|jpeg|png|pdf|docx))$", message = "File must be an image or document (jpg, jpeg, png, pdf, docx)")
    private String file;

    @Column(name = "priority_level", length = 10, nullable = false)
    @NotEmpty(message = "Priority level cannot be empty")
    @Pattern(regexp = "^(Normal|Urgent)$", message = "Priority level must be one of the following: Normal, Urgent")
    private String priority_level;

    @Column(name = "status", length = 13, nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;


    @ManyToOne
    @JsonIgnore
    private Investor investor;


    @ManyToOne
    @JsonIgnore
    private Owner owner;

    public enum ComplaintStatus {
        UNDER_REVIEW,
        IN_PROGRESS,
        RESOLVED
    }
}
