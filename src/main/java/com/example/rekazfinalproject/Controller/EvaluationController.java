package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Evaluation;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.EvaluationService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/evaluation")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(evaluationService.getAllEvaluations());
    }



    @GetMapping("/investor-received")
    public ResponseEntity getReceivedEvaluations(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(evaluationService.getEvaluationById(user.getId()));
    }

    @PostMapping("/request/{propertyId}/{investorId}")
    public ResponseEntity sendEvaluationRequest(@AuthenticationPrincipal User user, Evaluation evaluation ,@PathVariable Integer propertyId,@PathVariable Integer investorId) {
        evaluationService.sendEvaluationRequest(user.getId(),investorId,evaluation,propertyId);
        return ResponseEntity.status(200).body(new ApiResponse("Evaluation sent successfully"));
    }


    @PutMapping("/investor/respond/{evaluationId}")
    public ResponseEntity respondToEvaluation(@AuthenticationPrincipal User user, @RequestBody Evaluation evaluation,@PathVariable Integer evaluationId) {
        evaluationService.respondToEvaluation(user.getId(), evaluation,evaluationId);
        return ResponseEntity.status(200).body(new ApiResponse("Evaluation responded successfully"));
    }

    @DeleteMapping("/delete-evaluation/{evaluationId}")
    public ResponseEntity deleteEvaluation(@AuthenticationPrincipal User user, @PathVariable Integer evaluationId) {
        evaluationService.deleteEvaluation(user.getId(),evaluationId);
        return ResponseEntity.status(200).body(new ApiResponse("Evaluation removed successfully"));
    }
}
