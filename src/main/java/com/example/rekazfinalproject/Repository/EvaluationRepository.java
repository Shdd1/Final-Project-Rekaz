package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Evaluation;
import com.example.rekazfinalproject.Model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    Evaluation findEvaluationById(Integer id);

    List<Evaluation> findEvaluationByInvestor(Investor investor);
}
