package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Consultation;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/consultation")
public class ConsultationController {
    private final ConsultationService consultationService;


    @GetMapping("/get-all")
    public ResponseEntity getAllConsultations(){
        return ResponseEntity.status(200).body(consultationService.getAllConsultations());
    }
//

    @PostMapping("/book-consultation/{investorId}")
    public ResponseEntity addConsultation(@AuthenticationPrincipal User user,@PathVariable Integer investorId,@Valid @RequestBody Consultation consultation){
        consultationService.bookConsultation(user.getId(),investorId,consultation);
        return ResponseEntity.status(200).body(new ApiResponse("Consultation successfully added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateConsultation (@AuthenticationPrincipal User user,@PathVariable Integer id , @Valid @RequestBody Consultation consultation){
        consultationService.updateConsultation(user.getId(),id, consultation);
        return ResponseEntity.status(200).body(new ApiResponse("Consultation successfully updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteConsultation(@AuthenticationPrincipal User user,@PathVariable Integer id){
        consultationService.deleteConsultation(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("Consultation successfully deleted"));
    }
    @PutMapping("/extend-duration/{conId}/{duration}")
    public ResponseEntity extendConsultationDuration(@AuthenticationPrincipal User user,@PathVariable Integer conId,@PathVariable double duration){
        consultationService.extendConsultationDuration(user.getId(),conId,duration);
        return ResponseEntity.status(200).body(new ApiResponse("The duration has been updated successfully."));
    }
    @PutMapping("/canceled/owner-id/con-id/{conid}")
    public ResponseEntity ownerCanceledConsultation(@AuthenticationPrincipal User user,@PathVariable Integer conid){
        consultationService.ownerCanceledConsultation(user.getId(),conid);
        return ResponseEntity.status(200).body(new ApiResponse("Owner canceled consultation "));
    }
    @PutMapping("/canceled/investor-id/con-id/{conid}")
    public ResponseEntity  InvestorCanceledConsultation(@AuthenticationPrincipal User user,@PathVariable Integer conid){
        consultationService.investorCanceledConsultation(user.getId(),conid);
        return ResponseEntity.status(200).body(new ApiResponse("Investor canceled consultation"));
    }

    @GetMapping("/get-available-consultation/{investor}")
    public ResponseEntity getAvailableConsultationDates(@AuthenticationPrincipal User user,@PathVariable Integer investor){
        return ResponseEntity.status(200).body(consultationService.getAvailableConsultationDatesForInvestor(user.getId(),investor));
    }
    @GetMapping("/get-owner-consultation")
    public ResponseEntity listConsultationForOwner(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(consultationService.listConsultationForOwner(user.getId()));
    }

    @GetMapping("/get-investor-consultation")
    public ResponseEntity listConsultationForInvestor(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(consultationService.listConsultationsForInvestor(user.getId()));
    }
}