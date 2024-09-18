package com.example.rekazfinalproject.Controller;


import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Bid;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.BidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bid")

    // All CRUD by suliman

    public class BidController {

    private final BidService bidService;

    @GetMapping("/get-all-bids")
    public ResponseEntity getAllBids() {
        return ResponseEntity.status(200).body(bidService.findAllBids());
    }

    @PostMapping("/add-bid/{projectId}")
    public ResponseEntity addBid(@AuthenticationPrincipal User user , @PathVariable int projectId  , @Valid @RequestBody Bid bid  ) {
        bidService.addBid(user.getInvestor().getId(), projectId, bid);
        return ResponseEntity.status(200).body(new ApiResponse("Bid added successfully"));

    }

    @PutMapping("/edit-bid/{projectId}")
    public ResponseEntity editBid( @AuthenticationPrincipal User user , @PathVariable Integer projectId, @Valid  @RequestBody Bid bid) {
        bidService.editBid(user.getInvestor().getId() , projectId ,bid);
        return ResponseEntity.status(200).body(new ApiResponse("Bid edited successfully"));
    }

    @DeleteMapping("/delete-bid/{bidId}")
    public ResponseEntity deleteBid( @AuthenticationPrincipal User user , @PathVariable Integer bidId) {
        bidService.deleteBid( user.getInvestor().getId() , bidId );
        return ResponseEntity.status(200).body(new ApiResponse("Bid deleted successfully"));
    }

    @GetMapping("/get-project-bids/{projectId}")
    public ResponseEntity getProjectBids(@AuthenticationPrincipal User user , @PathVariable int projectId) {

        return ResponseEntity.status(200).body(bidService.getProjectBids(user.getId(),projectId));
    }

    // Suliman
    @GetMapping("/get-my-bids")
    public ResponseEntity getMyBids(@AuthenticationPrincipal User user ) {

        return ResponseEntity.status(200).body(bidService.getMyBids(user.getId()));
    }


}