package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.ContractDTO;
import com.example.rekazfinalproject.Model.Contract;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contract")


public class ContractController {

    private final ContractService contractService;

    // All crud by suliman , add contract logic by danah

    @GetMapping("/get-all-contracts")
    public ResponseEntity getAllContracts(){
        return ResponseEntity.status(200).body(contractService.getAllContract());
    }

    @PostMapping("/add-contract/{investorId}/{projectId}")

    public ResponseEntity addContract(@AuthenticationPrincipal User user , @PathVariable Integer investorId , @PathVariable Integer projectId , @Valid @RequestBody ContractDTO contractDTO){
        contractService.addContract(user.getOwner().getId(),investorId,projectId,contractDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Contract added successfully"));
    }

    @PutMapping("/update-contract/{contractId}")
    public ResponseEntity updateContract( @AuthenticationPrincipal User user , @PathVariable Integer contractId, @Valid  @RequestBody ContractDTO contractDTO){
        contractService.updateContract( user.getId() , contractId , contractDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Contract updated successfully"));
    }

    @DeleteMapping("/delete-contract/{contractId}")
    public ResponseEntity deleteContract( @AuthenticationPrincipal User user , @PathVariable Integer contractId){
        contractService.deleteContract(user.getId() , contractId );
        return ResponseEntity.status(200).body(new ApiResponse("Contract deleted successfully"));
    }

    @PutMapping("/approve-contract/{contractId}")
    public ResponseEntity approveContract(@AuthenticationPrincipal User user, @PathVariable Integer contractId) {
        contractService.approveContract(user.getInvestor().getId(), contractId);
        return ResponseEntity.status(200).body(new ApiResponse("Contract approved successfully"));
    }

    @PutMapping("/reject-contract/{contractId}")
    public ResponseEntity rejectContract(@AuthenticationPrincipal User user, @PathVariable Integer contractId) {
        contractService.rejectContract(user.getInvestor().getId(), contractId);
        return ResponseEntity.status(200).body(new ApiResponse("Contract rejected successfully"));
    }

    // Suliman

    @GetMapping("/get-my-contracts-owner")
    public ResponseEntity getMyContractsOwner(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(contractService.getMyContractsOwner(user.getId()));
    }

    // Suliman

    @GetMapping("/get-my-contracts-investor")
    public ResponseEntity getMyContractsInvestor(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(contractService.getMyContractsInvestor(user.getId()));
    }


}
