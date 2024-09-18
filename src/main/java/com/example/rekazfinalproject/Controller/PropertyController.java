package com.example.rekazfinalproject.Controller;
import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.DTO.PropertyDTO;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {


    private final PropertyService propertyService;


    @GetMapping("/get-all-properties")
    public ResponseEntity getAllProperty(){

        return ResponseEntity.status(200).body(propertyService.getAllProperty());
    }


    @GetMapping("/get-my-properties")
    public ResponseEntity getMyProperty(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(propertyService.getMyProperty(user.getId()));
    }
    @PostMapping("/add-property")
    public ResponseEntity addProperty(@AuthenticationPrincipal User user, @Valid @RequestBody PropertyDTO propertyDTO) {
        propertyService.addProperty(user.getId(),propertyDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Property added successfully"));
    }

    @PutMapping("/update-property/{propertyId}")
    public ResponseEntity updateProperty(@AuthenticationPrincipal User user,@PathVariable Integer propertyId, @Valid @RequestBody PropertyDTO propertyDTO) {
        propertyService.updateProperty(user.getId(),propertyId, propertyDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Property updated successfully"));
    }

    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity deleteProperty(@AuthenticationPrincipal User user,@PathVariable Integer propertyId) {
        propertyService.deleteProperty(user.getId(),propertyId);
        return ResponseEntity.status(200).body(new ApiResponse("Property deleted successfully"));
    }

}
