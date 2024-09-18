package com.example.rekazfinalproject;

import com.example.rekazfinalproject.Controller.ReportController;
import com.example.rekazfinalproject.Model.Report;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ReportService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ReportController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ReportControllerTest {

    @MockBean
    private ReportService reportService;

    @Autowired
    private MockMvc mockMvc;

    private Report report1, report2;
    private List<Report> reports;

    @BeforeEach
    void setUp() {
        report1 = new Report();
        report1.setId(1);
        report1.setCreatedAt(LocalDate.now());
        report1.setContent("Content1");

        report2 = new Report();
        report2.setId(2);
        report2.setCreatedAt(LocalDate.now());
        report2.setContent("Content2");

        reports = Arrays.asList(report1, report2);
    }

    @Test
    public void testDeleteReport() throws Exception {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);

        mockMvc.perform(delete("/api/v1/reports/delete/{id}", 1)
                        .principal(() -> "user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Report Deleted Successfully!"));

        verify(reportService, times(1)).deleteReport(anyInt(), anyInt());
    }

}
