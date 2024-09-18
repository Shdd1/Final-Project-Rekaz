package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    //*** All CRUD Done by Danah ****
    //Done test - get
    @GetMapping("/get-all-users")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(userService.getAllUsers());

    }

    //done test - Add User
    @PostMapping("/add-user")
    public ResponseEntity addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
    }

    //Done test - Update User
    @PutMapping("/update-user")
    public ResponseEntity updateUser(@AuthenticationPrincipal User user, @Valid @RequestBody User user1){
        userService.updateUser(user.getId(), user1);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    //Done test - Delete User
    @DeleteMapping("/delete-user")
    public ResponseEntity deleteUser(@AuthenticationPrincipal User user){
        userService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }

    // Danah
    @PutMapping("/activate/{userId}")
    public ResponseEntity activateUser(@AuthenticationPrincipal User user , @PathVariable Integer userId){
        userService.activateUser(user.getId() , userId);
        return ResponseEntity.status(200).body(new ApiResponse("User activated successfully"));
    }

    // Suliman

    @PutMapping("/discount/{ownerId}")
    public ResponseEntity discountOwner(@AuthenticationPrincipal User user , @PathVariable Integer ownerId){
        userService.discountOwner(user.getId() , ownerId);
        return ResponseEntity.status(200).body(new ApiResponse("discount made to owner successfully"));
    }


    // Suliman

    @DeleteMapping("/delete-by-word/{word}")
    public ResponseEntity deleteByComment( @AuthenticationPrincipal User user ,  @PathVariable String word)
    {
        userService.deleteByWord( user.getId(), word);
        return ResponseEntity.status(200).body(new ApiResponse("Comment deleted successfully"));
    }

    // Suliman

    @PutMapping("/answer-question/{questionId}")
    public ResponseEntity addAnswer( @AuthenticationPrincipal User user , @PathVariable Integer questionId, @RequestBody String answer)
    {
        userService.answerQuestion(user.getId() , questionId, answer);
        return ResponseEntity.status(200).body((new ApiResponse("Question answered successfully")));
    }

    // Suliman

    @GetMapping("/report-owners")
    public ResponseEntity reportOwners(){
        return ResponseEntity.status(200).body(userService.reportOwners());
    }


    // Suliman

    @GetMapping("/report-investors")
    public ResponseEntity reportInvestor(){
        return ResponseEntity.status(200).body(userService.reportInvestors());
    }



}
