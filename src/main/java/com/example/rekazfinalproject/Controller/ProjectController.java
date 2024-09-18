package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")

// All CRUD by suliman

public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get-all-projects")
    public ResponseEntity getAllProjects() {
        return ResponseEntity.status(200).body(projectService.getAllProjects());
    }

    @PostMapping("/add-project/{propertyId}")
    public ResponseEntity addProject(@AuthenticationPrincipal User user , @PathVariable Integer propertyId,  @Valid @RequestBody Project project) {
        projectService.addProject( user.getOwner().getId() , propertyId , project);
        return ResponseEntity.status(200).body(new ApiResponse("Project added successfully"));
    }

    @PutMapping("/update-project/{projectId}")
    public ResponseEntity updateProject( @AuthenticationPrincipal User user  , @PathVariable  Integer projectId , @Valid  @RequestBody Project project) {
        projectService.updateProject( user.getOwner().getId() , projectId , project);
        return ResponseEntity.status(200).body(new ApiResponse("Project updated successfully"));
    }

    @DeleteMapping("/delete-project/{projectId}")
    public ResponseEntity deleteProject( @AuthenticationPrincipal User user  , @PathVariable  Integer projectId) {
        projectService.deleteProject( user.getOwner().getId() , projectId);
        return ResponseEntity.status(200).body(new ApiResponse("Project deleted successfully"));
    }

    //-- Danah
    @GetMapping("/get-projects-by-city/{city}")
    public ResponseEntity getAllProjectsByCity(@PathVariable String city) {
        List<Project> projects = projectService.findByCity(city);
        return ResponseEntity.status(200).body(projects);
    }

    //-- Danah
    @GetMapping("/get-projects-by-type/{projectType}")
    public ResponseEntity getAllProjectsByProjectType(@PathVariable String projectType) {
        List<Project> projects = projectService.findByProjectType(projectType);
        return ResponseEntity.status(200).body(projects);
    }


    // Suliman
    @GetMapping("/get-closest-projects")
    public ResponseEntity getClosestProject() {
        return ResponseEntity.status(200).body(projectService.getClosestProjects());
    }

    // Suliman


    @GetMapping("/get-highest-projects")
    public ResponseEntity getHighestProject() {
        return ResponseEntity.status(200).body(projectService.getHighestProjectsBudget());
    }

    // Suliman


    @GetMapping("/get-projects-by-date/{startDate}/{endDate}")
    public ResponseEntity getByDate(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        return ResponseEntity.status(200).body(projectService.getProjectByDate(startDate, endDate));
    }

    // Shahad

    @GetMapping("/get-projects-by-budget/{maxBudget}")
    public ResponseEntity getProjectsByBudget(@AuthenticationPrincipal User user , @PathVariable double maxBudget ) {
        return ResponseEntity.status(200).body(projectService.getProjectsByBudget(user.getInvestor().getId(),maxBudget));
    }

}
