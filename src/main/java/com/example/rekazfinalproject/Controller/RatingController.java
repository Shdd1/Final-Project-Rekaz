package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Rating;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    @GetMapping("/get-all-ratings")
    public ResponseEntity getAllRating(){
        return ResponseEntity.status(200).body(ratingService.getAllRating());
    }
    //    //owner
    @GetMapping("/get-my-ratings")
    public ResponseEntity getMyRating(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(ratingService.getMyRating(user.getId()));
    }

    @PostMapping("/add-rating/{investorId}")
    public ResponseEntity addRating(@AuthenticationPrincipal User user, @PathVariable Integer investorId , @Valid @RequestBody Rating rating){
        ratingService.addNewRating(user.getId(),investorId,rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully added"));
    }

    @PutMapping("/update-rating/{ratingId}")
    public ResponseEntity updateRating(@AuthenticationPrincipal User user ,@PathVariable Integer ratingId, @Valid @RequestBody Rating rating){
        ratingService.updateRating(user.getId(),ratingId, rating);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully updated"));
    }

    @DeleteMapping("/delete-rating/{ratingId}")
    public ResponseEntity deleteRating(@AuthenticationPrincipal User user,@PathVariable Integer ratingId){
        ratingService.deleteRating(user.getId(),ratingId);
        return ResponseEntity.status(200).body(new ApiResponse("Rating successfully deleted"));
    }
    //    //average rating
    @GetMapping("/get-average/{investorId}")
    public ResponseEntity getAverageRating( @AuthenticationPrincipal User user , @PathVariable Integer investorId){
        return ResponseEntity.status(200).body(new ApiResponse("Average Rating: " +ratingService.getAverageRating(investorId)));
    }

    // Suliman

    @GetMapping("/get-investor-ratings/{investorId}")
    public ResponseEntity getInvestorRatings( @AuthenticationPrincipal User user , @PathVariable Integer investorId){
        return ResponseEntity.status(200).body(ratingService.getRatingByInvestor(investorId));
    }


}

