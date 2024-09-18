package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.AvailableDateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/available-date")

    // All CRUD by suliman


    public class AvailableDateController {

    private final AvailableDateService availableDateService;

    @GetMapping("/get-all-available-dates")
    public ResponseEntity getAllAvailableDates(){
        return ResponseEntity.status(200).body(availableDateService.getAllAvailableDate());
    }

    // suliman

    @GetMapping("/get-investor-available-dates")
    public ResponseEntity getInvestorAvailableDates(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(availableDateService.getInvestorAvailableDates(user.getId()));
    }

    @PostMapping("/add-available-date")
    public ResponseEntity addAvailableDate( @AuthenticationPrincipal User user, @Valid @RequestBody AvailableDate availableDate) {
        availableDateService.addAvailableDate(user.getId(), availableDate);
        return ResponseEntity.status(200).body(new ApiResponse("Available date added successfully"));
    }


    @PutMapping("/update-available-date/{id}")
    public ResponseEntity updateAvailableDate(@AuthenticationPrincipal User user,@PathVariable Integer id, @Valid @RequestBody AvailableDate availableDate){
        availableDateService.updateAvailableDate(user.getId(), id, availableDate);
        return ResponseEntity.status(200).body(new ApiResponse("Available date updated successfully"));
    }

    @DeleteMapping("/delete-available-date/{id}")
    public ResponseEntity deleteAvailableDate(@AuthenticationPrincipal User user,@PathVariable Integer id){
        availableDateService.deleteAvailableDate(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("Available date deleted successfully"));
    }
}
