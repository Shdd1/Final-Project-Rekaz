package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Question findQuestionById(Integer id);
}