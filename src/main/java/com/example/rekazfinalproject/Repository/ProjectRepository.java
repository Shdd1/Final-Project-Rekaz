package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findProjectById(int id);

    Integer countProjectByOwnerId(Integer id);

    List<Project> findProjectByCity(String city);

    List<Project> findProjectByProjectType(String projectType);

    Project findProjectByOwnerId(Integer ownerId);
    
   Project findProjectByInvestorId(Integer investorId);
    
    @Query("SELECT p FROM Project p WHERE p.budget <= :budget and p.status='PENDING'")
        List<Project> listProjectWithSpecvicedBudgetAndStatus(double budget);
    
    List<Project>findProjectsByOwnerId(Integer ownerId);

    @Query("select p from Project p where p.startDate>=?1 and p.startDate<=?2")
    List<Project> findByProjectDateBetween(LocalDate start, LocalDate end);
    

}
