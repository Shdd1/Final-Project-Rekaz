package com.example.rekazfinalproject;


import com.example.rekazfinalproject.Model.Investor;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.Report;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.InvestorRepository;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import com.example.rekazfinalproject.Repository.ReportRepository;
import com.example.rekazfinalproject.Repository.UserRepository;
import com.example.rekazfinalproject.Service.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @InjectMocks
    ReportService reportService;
    @Mock
    ReportRepository reportRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    InvestorRepository investorRepository;

    @Mock
    ProjectRepository projectRepository;

    User user;
    Investor investor;
    Project project;
    Report report1, report2, report3;
    List<Report> reports;

    @BeforeEach
    public void setUp() {

        user = new User(null,"dana","password@123","dana@gmail.com","OWNER",true,null,null,null);

        investor = new Investor();
        investor.setId(1);


        project = new Project();
        project.setId(1);

        report1 = new Report();
        report1.setId(1);
        report1.setContent("Report 1 content");
        report1.setCreatedAt(LocalDate.now());
        report1.setInvestor(investor);
        report1.setProject(project);

        report2 = new Report();
        report2.setId(2);
        report2.setContent("Report 2 content");
        report2.setCreatedAt(LocalDate.now());
        report2.setInvestor(investor);
        report2.setProject(project);

        reports = Arrays.asList(report1, report2);
    }

    @Test
    public void testGetAllReports() {
        when(userRepository.findUserById(1)).thenReturn(user);
        when(reportRepository.findAll()).thenReturn(reports);

        List<Report> result = reportService.getAllReports(1);
        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findAll();
    }


    @Test
    public void testCreateReport() {
        when(investorRepository.findInvestorById(1)).thenReturn(investor);
        when(projectRepository.findProjectById(1)).thenReturn(project);
        when(reportRepository.save(any(Report.class))).thenReturn(report1);

        reportService.createReport(1, 1, "New report content");

        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    public void testUpdateReport() {
        when(userRepository.findUserById(1)).thenReturn(user);
        when(reportRepository.findReportById(1)).thenReturn(report1);
        when(reportRepository.save(any(Report.class))).thenReturn(report1);

        Report updatedReport = new Report();
        updatedReport.setCreatedAt(LocalDate.now());
        updatedReport.setContent("Updated report content");

        reportService.updateReport(1, 1, updatedReport);

        verify(reportRepository, times(1)).save(report1);
        assertEquals("Updated report content", report1.getContent());
    }

    @Test
    public void testDeleteReport() {
        when(userRepository.findUserById(1)).thenReturn(user);
        when(reportRepository.findReportById(1)).thenReturn(report1);

        reportService.deleteReport(1, 1);

        verify(reportRepository, times(1)).delete(report1);
    }

    @Test
    public void testGetReportsByProjectId() {
        when(userRepository.findUserById(1)).thenReturn(user);
        when(reportRepository.findByProjectId(1)).thenReturn(reports);

        List<Report> result = reportService.getReportsByProjectId(1, 1);
        assertEquals(2, result.size());
        verify(reportRepository, times(1)).findByProjectId(1);
    }
}
