package com.example.rekazfinalproject.Service;


import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.AvailableDate;
import com.example.rekazfinalproject.Model.Investor;
import com.example.rekazfinalproject.Repository.AvailableDateRepository;
import com.example.rekazfinalproject.Repository.InvestorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AvailableDateService {

    private final AvailableDateRepository availableDateRepository;
    private final InvestorRepository investorRepository;

    public List<AvailableDate> getAllAvailableDate(){
        return availableDateRepository.findAll();
    }

    public void addAvailableDate( Integer investorId , AvailableDate availableDate) {
        Investor investor = investorRepository.findInvestorById(investorId);
        if(investor==null){
            throw new ApiException("Investor not found");
        }

        for(AvailableDate availableDate1 : availableDateRepository.findAll()){
            if(availableDate1.getInvestor().getId()==investorId && availableDate1.getDate().equals(availableDate.getDate())  ){
                throw new ApiException("this date already added");
            }
        }
        availableDate.setInvestor(investor);
        availableDateRepository.save(availableDate);
    }

    public List<AvailableDate> getInvestorAvailableDates(int investorId){
        List<AvailableDate> investorAvailableDates = new ArrayList<>();
        Investor investor = investorRepository.findInvestorById(investorId);
        if(investor==null){
            throw new ApiException("Investor not found");
        }
        for(AvailableDate availableDate : availableDateRepository.findAll()){
            if(availableDate.getInvestor().getId()==investor.getId()){
                investorAvailableDates.add(availableDate);
            }
        }
        if(investorAvailableDates.isEmpty()){
            throw new ApiException("Investor doesn't have any available dates");
        }
        return investorAvailableDates;

    }

    public void updateAvailableDate(Integer userId,Integer id , AvailableDate availableDate) {
        AvailableDate availableDate1 = availableDateRepository.findAvailableDateById(id);
        if(availableDate1==null){
            throw new ApiException("Investor not found");
        }
        availableDate1.setDate(availableDate.getDate());
        availableDate1.setBooked(availableDate.getBooked());
        availableDateRepository.save(availableDate1);
    }

    public void deleteAvailableDate(Integer userId,Integer id) {
        AvailableDate availableDate = availableDateRepository.findAvailableDateById(id);
        if(availableDate==null){
            throw new ApiException("Investor not found");
        }
        if(availableDate.getBooked()){
            throw new ApiException("Booked date can't be deleted");
        }


        availableDateRepository.delete(availableDate);
    }
}



