package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.Evaluation;
import com.example.rekazfinalproject.Model.Investor;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Property;
import com.example.rekazfinalproject.Repository.EvaluationRepository;
import com.example.rekazfinalproject.Repository.InvestorRepository;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final PropertyRepository propertyRepository;
    private final InvestorRepository investorRepository;
    private final OwnerRepository ownerRepository;

    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    public List<Evaluation> getEvaluationById(Integer id) {
        Investor investor = investorRepository.findInvestorById(id);

        return evaluationRepository.findEvaluationByInvestor(investor);
    }



    public void sendEvaluationRequest(Integer userId,Integer investorId,Evaluation evaluation,Integer propertyId ) {
        Property property = propertyRepository.findPropertyById(propertyId);
        Investor investor = investorRepository.findInvestorById(investorId);

        if (property == null) {
            throw new ApiException("Property with ID " + propertyId + " not found.");
        }

        if (investor == null) {
            throw new ApiException("Investor with ID " + investorId + " not found.");
        }

        Owner owner = ownerRepository.findOwnerByPropertiesContaining(property);
        if (owner == null) {
            throw new ApiException("Owner does not own this property.");
        }

        if(owner.getSubscription()!=null){
            evaluation.setPrice(0);
        }
        evaluation.setStatus(Evaluation.Status.IN_PROGRESS);
        evaluation.setInvestor(investor);
        evaluation.setOwner(owner);
        evaluationRepository.save(evaluation);
        System.out.println("Request sent to investor " + investor.getUser().getUsername() + " for property: " + property.getId());
    }

    public void respondToEvaluation(Integer investorId, Evaluation evaluation,Integer evaluationId) {
        Evaluation existingEvaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new ApiException("Evaluation not found."));

        if (!existingEvaluation.getInvestor().getId().equals(investorId)) {
            throw new ApiException("Investor not authorized to respond to this evaluation.");
        }

        existingEvaluation.setProjectType(evaluation.getProjectType());
        existingEvaluation.setDescription(evaluation.getDescription());
        existingEvaluation.setMinBudget(evaluation.getMinBudget());
        existingEvaluation.setMaxBudget(evaluation.getMaxBudget());
        existingEvaluation.setRecommendation(evaluation.getRecommendation());
        existingEvaluation.setStatus(Evaluation.Status.COMPLETED);
        evaluationRepository.save(existingEvaluation);
    }






    public void deleteEvaluation(Integer userId, Integer evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }
}

