package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.Model.Investor;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.InvestorService;
import com.example.rekazfinalproject.Service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final InvestorService investorService;

    //*** All CRUD Done by Danah ****
    @GetMapping("/get-all-owners")
    public ResponseEntity getAllOwners(){
        return ResponseEntity.status(200).body(ownerService.getAllOwners());
    }

    @PostMapping("/register-owner")
    public ResponseEntity registerOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.registerOwner(ownerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Registered owner successfully"));
    }

    @PutMapping("/update-owner")
    public ResponseEntity updateOwner(@AuthenticationPrincipal User user, @Valid @RequestBody OwnerDTO ownerDTO) {
        ownerService.updateOwner(user.getOwner().getId(), ownerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Updated owner successfully"));
    }

    @DeleteMapping("/delete-owner")
    public ResponseEntity deleteOwner(@AuthenticationPrincipal User user) {
        ownerService.deleteOwner(user.getOwner().getId());
        return ResponseEntity.status(200).body(new ApiResponse("Deleted owner successfully"));
    }

    // Suliman

    @GetMapping("/get-my-projects")
    public ResponseEntity getMyProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(ownerService.getMyProjects(user.getOwner().getId()));
    }



    // Suliman
    @PutMapping("/approve-bid/{bidId}")
    public ResponseEntity approveBid(@AuthenticationPrincipal User user, @PathVariable int bidId) {
        ownerService.approveBid(user.getOwner().getId() , bidId);
        return ResponseEntity.status(200).body(new ApiResponse("Bid Approved successfully"));
    }

    // Suliman

    @PutMapping("/reject-bid/{bidId}")
    public ResponseEntity rejectBid(@AuthenticationPrincipal User user, @PathVariable int bidId , @RequestBody String comment) {
        ownerService.rejectBid(user.getOwner().getId() , bidId , comment);
        return ResponseEntity.status(200).body(new ApiResponse("Bid Rejected successfully"));
    }


    //Danah
    @GetMapping("get-investor-projects/{investorId}")
    public ResponseEntity getInvestorProjects(@PathVariable Integer investorId) {
        return ResponseEntity.status(200).body(investorService.getInvestorProject(investorId));
    }
    @GetMapping("/investors-with-contract")public ResponseEntity getInvestorsWithContract(@AuthenticationPrincipal User user) {
        List<Investor> investors = ownerService.getInvestorsWithContract(user.getId());    return ResponseEntity.status(200).body(investors);
    }


}
