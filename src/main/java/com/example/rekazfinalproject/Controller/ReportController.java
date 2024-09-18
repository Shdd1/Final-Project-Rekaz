package com.example.rekazfinalproject.Controller;


import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Report;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/get")
    public ResponseEntity getAllReports(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(reportService.getAllReports(user.getId()));
    }

    @PostMapping("/create-report/{projectId}")
    public ResponseEntity createReport(@AuthenticationPrincipal User user,@PathVariable Integer projectId, @Valid @RequestBody String content) {

        reportService.createReport(user.getId(),projectId, content);
        return ResponseEntity.status(200).body(new ApiResponse("Report created successfully"));
    }

    // Update a report by ID
    @PutMapping("/update/{id}")
    public ResponseEntity updateReport( @AuthenticationPrincipal User user,@PathVariable Integer id, @RequestBody Report report) {
        reportService.updateReport(user.getId(),id, report);
        return ResponseEntity.status(200).body(new ApiResponse("Report updated successfully"));
    }

    // Delete a report by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReport(@AuthenticationPrincipal User user,@PathVariable Integer id) {
        reportService.deleteReport(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("Report deleted successfully"));
    }

    @GetMapping("/owner/{projectId}")
    public ResponseEntity getReportsByProjectId(@AuthenticationPrincipal User user,@PathVariable Integer projectId) {
        List<Report> reports = reportService.getReportsByProjectId(user.getId(),projectId);
        return ResponseEntity.status(200).body(reports);
    }


}