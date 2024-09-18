package com.example.rekazfinalproject.Service;


import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final InvestorRepository investorRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    // All Done By Danah
    public List<Report> getAllReports(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return reportRepository.findAll();
    }



    public void createReport(Integer investorId,Integer projectId, String content) {
        Investor investor = investorRepository.findInvestorById(investorId);
        if (investor == null) {
            throw new ApiException("not found investor");
        }


        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ApiException("not found project");
        }
        Report report = new Report();
        report.setCreatedAt(LocalDate.now());
        report.setContent(content);
        report.setInvestor(investor);
        report.setProject(project);
        reportRepository.save(report);

    }

    // Update a report
    public void updateReport(Integer userId,Integer id, Report report) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        Report r = reportRepository.findReportById(id);
        if (r == null) {
            throw new ApiException("not found report");
        }
        r.setCreatedAt(report.getCreatedAt());
        r.setContent(report.getContent());
        reportRepository.save(r);
    }

    // Delete report
    public void deleteReport(Integer userId,Integer id) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        Report report = reportRepository.findReportById(id);
        if (report == null) {
            throw new ApiException("not found report");
        }
        reportRepository.delete(report);
    }

    public List<Report> getReportsByProjectId(Integer userId,Integer projectId) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        return reportRepository.findByProjectId(projectId);
    }
}