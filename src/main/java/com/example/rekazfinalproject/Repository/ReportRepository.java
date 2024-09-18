package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByProjectId(Integer projectId);

    Report findReportById(Integer reportId);
}
