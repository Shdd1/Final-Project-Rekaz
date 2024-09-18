package com.example.rekazfinalproject.Controller;

import com.example.rekazfinalproject.Api.ApiResponse;
import com.example.rekazfinalproject.Model.Question;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")

// All CRUD by suliman

public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/get-all-questions")
    public ResponseEntity getAllQuestion()
    {
        return ResponseEntity.status(200).body(questionService.getAllQuestion());
    }

    @PostMapping("/add-question")
    public ResponseEntity addQuestion(@AuthenticationPrincipal User user , @RequestBody String question) {
        questionService.addQuestion(user.getId(),question);
        return ResponseEntity.status(200).body(new ApiResponse("Question added successfully"));
    }


    @PutMapping("/update-question/{questionId}")
    public ResponseEntity updateQuestion(@PathVariable Integer questionId, @Valid @RequestBody Question question)
    {
        questionService.updateQuestion(questionId, question);
        return ResponseEntity.status(200).body(new ApiResponse("Question updated successfully"));
    }

    @DeleteMapping("/delete-question/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable Integer questionId)
    {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.status(200).body(new ApiResponse("Question deleted successfully"));
    }


}