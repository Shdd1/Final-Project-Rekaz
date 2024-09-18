package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Investor;
import com.example.rekazfinalproject.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Integer> {
    Investor findInvestorById(Integer id);
    @Query("SELECT DISTINCT i FROM Investor i JOIN i.bids b WHERE b.project IN :projects")
    List<Investor> findInvestorsByProjects(List<Project> projects);
}
