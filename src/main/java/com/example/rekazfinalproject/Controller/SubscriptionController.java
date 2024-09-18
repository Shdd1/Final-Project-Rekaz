package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.SubscriptionDTO;
import com.example.rekazfinalproject.Model.Subscription;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor

public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/get-all-subscriptions")
    public ResponseEntity getAllSubscriptions(){
        return ResponseEntity.status(200).body(subscriptionService.getAllSubscriptions());
    }

    @PostMapping("/add-subscription")
    public ResponseEntity addSubscription(@AuthenticationPrincipal User user , @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        subscriptionService.addSubscription(user.getId(), subscriptionDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully added subscription"));
    }

    @PutMapping("/update-subscription/{subscriptionId}")
    public ResponseEntity updateSubscription(@PathVariable int subscriptionId , @Valid @RequestBody  Subscription subscription) {
        subscriptionService.updateSubscription(subscriptionId, subscription);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated subscription"));
    }

    @DeleteMapping("/delete-subscription/{subscriptionId}")
    public ResponseEntity deleteSubscription(@PathVariable int subscriptionId ) {
        subscriptionService.deleteSubscription(subscriptionId);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted subscription"));
    }

}
