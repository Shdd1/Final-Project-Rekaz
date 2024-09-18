package com.example.rekazfinalproject.Controller;


import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.InvestorDTO;
import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.AvailableDateService;
import com.example.rekazfinalproject.Service.BidService;
import com.example.rekazfinalproject.Service.InvestorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/investor")
@RequiredArgsConstructor
public class InvestorController {

    //*** All CRUD Done by Danah ****
    private final InvestorService investorService;

    @GetMapping("/get")
    public ResponseEntity getAllInvestor(){
        return ResponseEntity.status(200).body(investorService.getAllInvestor());
    }

    @PostMapping("/register")
    public ResponseEntity registerInvestor(@Valid @RequestBody InvestorDTO investorDTO) {
        investorService.registerInvestor(investorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Registered Successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateInvestor(@AuthenticationPrincipal User user, @Valid @RequestBody InvestorDTO investorDTO) {
        investorService.updateInvestor(user.getInvestor().getId(), investorDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Updated Successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteInvestor(@PathVariable Integer id) {
        investorService.deleteInvestor(id);
        return ResponseEntity.status(200).body(new ApiResponse("Deleted Successfully"));
    }


    // Suliman

    @GetMapping("/get-my-projects")
    public ResponseEntity getMyProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(investorService.getMyProjects(user.getId()));
    }

    // Suliman

    @GetMapping("get-owner-projects/{ownerId}")
    public ResponseEntity getOwnerProjects( @AuthenticationPrincipal User user , @PathVariable int ownerId) {
        return ResponseEntity.status(200).body(investorService.getOwnerProject(ownerId));
    }

    // Suliman
    @GetMapping("/show-highest-rate")
    public ResponseEntity showHighestRate() {
        return ResponseEntity.status(200).body(investorService.showHighestInvestorsRate());
    }

    // Shahad

    @GetMapping("/get-investor-by-owner")
    public ResponseEntity listInvestorCompanyByOwner(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(investorService.listInvestorCompanyByOwner(user.getId()));
    }

}
