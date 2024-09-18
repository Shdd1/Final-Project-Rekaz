package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    public final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AvailableDateRepository availableDateRepository;
    private final OwnerRepository ownerRepository;
    private final InvestorRepository investorRepository;

    //shahad
    //all consultation must be admin
    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    //shahad
    public void bookConsultation(Integer ownerId, Integer availableDateId, Consultation consultation) {
        // Find the owner and check his role
        User ownerUser = userRepository.findUserById(ownerId);
        if (ownerUser == null || !ownerUser.getRole().equalsIgnoreCase("Owner")) {
            throw new ApiException("Owner not found");
        }
        if(ownerUser.isActive()==false){
            throw new ApiException("owner dose not active");
        }
        AvailableDate availableDate=availableDateRepository.findAvailableDateById(availableDateId);
        if(availableDate==null||availableDate.getBooked()==true){
            throw new ApiException("available Date not found");
        }
        availableDate.setBooked(true);
        if(ownerUser.getOwner().getSubscription()!=null){
            consultation.setConsultationFee(0);
        }
        else {
            consultation.setConsultationFee(consultation.getDuration() * 60);
            double percent = ownerUser.getOwner().getDiscountPercentage()/100;
            consultation.setConsultationFee(consultation.getConsultationFee()-(consultation.getConsultationFee()*percent));
        }
        consultation.setStatus(Consultation.ConsultationStatus.IN_PROGRESS);
        consultation.setOwner(ownerUser.getOwner());
        consultation.setConsultationDate(availableDate.getDate());
        consultation.setInvestor(availableDate.getInvestor());
        consultationRepository.save(consultation);
    }


    //shahad
    public void updateConsultation(Integer userid,Integer consultId, Consultation consultation) {
        User user=userRepository.findUserById(userid);
        Consultation consultation1 = consultationRepository.findConsultationById(consultId);
        if(user==null){
            throw new ApiException("User not found");
        }
        if (consultation1 == null) {
            throw new ApiException("Consultation not found");
        }
        consultation1.setConsultationDate(consultation.getConsultationDate());
        consultation1.setDuration(consultation.getDuration());
        consultationRepository.save(consultation1);
    }

    //shahad
    public void deleteConsultation(Integer adminId,Integer id) {
        Consultation consultation1 = consultationRepository.findConsultationById(id);
        if (consultation1 == null) {
            throw new ApiException("Consultation not found");
        }
        User adminUser=userRepository.findUserById(adminId);
        if(adminUser==null||!adminUser.getRole().equalsIgnoreCase("ADMIN")){
            throw new ApiException("You do not have the authority");
        }
        consultationRepository.delete(consultation1);
    }

    //shahad
    //extendConsultationDuration
    public void extendConsultationDuration(Integer ownerId, Integer consultationId, double additionalDuration) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        Consultation consultation = consultationRepository.findConsultationById(consultationId);

        if (consultation == null || owner == null) {
            throw new ApiException("Consultation or owner not found");
        }
        if (consultation.getOwner() == null || !consultation.getOwner().getId().equals(owner.getId())) {
            throw new ApiException("Owner does not own this consultation or consultation does not exist");
        }
        // can not extend if it is canceled
        if (consultation.getStatus() == Consultation.ConsultationStatus.CANCELED) {
            throw new ApiException("it is already canceled");
        }
        LocalDateTime currentEndTime = consultation.getConsultationDate()
                .plusHours((long) consultation.getDuration())
                .plusMinutes((long) ((consultation.getDuration() % 1) * 60));

        // Check if the consultation has already ended
        if (currentEndTime.isBefore(LocalDateTime.now())) {
            throw new ApiException("The consultation has already ended, and you cannot extend the duration.");
        }
        consultation.setOwner(owner);
        consultation.setInvestor(consultation.getInvestor());
        consultation.setDuration(consultation.getDuration() + additionalDuration);
        if(owner.getSubscription()!=null){
            consultation.setConsultationFee(0);
        }
        else {
            consultation.setConsultationFee(consultation.getDuration() * 60);
        }
        consultationRepository.save(consultation);
    }

    //Shahad
    //Owner cancelled consultation
    public void ownerCanceledConsultation(Integer ownerId, Integer conId) {
        LocalDate currentDate = LocalDate.now();
        Owner owner = ownerRepository.findOwnerById(ownerId);
        Consultation consultation = consultationRepository.findConsultationById(conId);
        if (owner == null || consultation == null) {
            throw new ApiException("owner or consultation not found");
        }
        if (!consultation.getOwner().equals(owner)) {
            throw new ApiException("owner has no consultation");
        }
        // can not cancel if it is canceled
        if (consultation.getStatus() == Consultation.ConsultationStatus.CANCELED) {
            throw new ApiException("it is already canceled");
        }
        boolean canCancel = consultationRepository.canCancelBeforeConsultationDate(conId, currentDate);
        if (!canCancel) {
            throw new ApiException("Cannot cancel the consultation on or after the consultation date");
        }
        consultation.setOwner(owner);
        consultation.setInvestor(consultation.getInvestor());
        AvailableDate availableDate = availableDateRepository.findAvailableDateById(conId);
        availableDate.setBooked(false);
        availableDateRepository.save(availableDate);
        consultation.setStatus(Consultation.ConsultationStatus.CANCELED);
        consultationRepository.save(consultation);
    }
    //
//    //Shahad
    //investor cancelled consultation
    public void investorCanceledConsultation(Integer investorId, Integer conId) {
        LocalDate currentDate = LocalDate.now();
        Investor investor = investorRepository.findInvestorById(investorId);
        Consultation consultation = consultationRepository.findConsultationById(conId);
        if (investor == null || consultation == null) {
            throw new ApiException("investor or consultation not found");
        }
        if (!consultation.getInvestor().equals(investor)) {
            throw new ApiException("investor has no consultation");
        }
        // can not cancel if it is canceled
        if (consultation.getStatus() == Consultation.ConsultationStatus.CANCELED) {
            throw new ApiException("it is already canceled");
        }
        boolean canCancel = consultationRepository.canCancelBeforeConsultationDate(conId, currentDate);
        if (!canCancel) {
            throw new ApiException("Cannot cancel the consultation on or after the consultation date");
        }
        consultation.setInvestor(investor);
        consultation.setOwner(consultation.getOwner());
        AvailableDate availableDate = availableDateRepository.findAvailableDateById(investorId);
        availableDate.setBooked(false);
        availableDateRepository.save(availableDate);
        consultation.setStatus(Consultation.ConsultationStatus.CANCELED);
        consultationRepository.save(consultation);
    }
    //shahad
    //get available consultation
    public List<AvailableDate> getAvailableConsultationDatesForInvestor(Integer ownerId,Integer investorId) {
        Owner owner=ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("Owner not found");
        }
        // Find the investor and verify his role
        User investorUser = userRepository.findUserById(investorId);
        if (investorUser == null || !investorUser.getRole().equalsIgnoreCase("Investor")) {
            throw new ApiException("Investor not found");
        }
        // Fetch all available dates for the investor
        List<AvailableDate> availableDates = availableDateRepository.findAvailableDatesByInvestorId(investorId);
        // Filter out booked dates
        List<AvailableDate> freeDates = new ArrayList<>();
        for (AvailableDate availableDate : availableDates) {
            if (!availableDate.getBooked()) {
                freeDates.add(availableDate);
            }
        }
        // Return the list of available dates
        return freeDates;
    }

    //shahad
    //list consultation for owner
    public List<Consultation> listConsultationForOwner(Integer ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Owner not found");
        }
        List<Consultation> consultations = consultationRepository.findConsultationByOwnerId(ownerId);
        if (consultations.isEmpty()) {
            throw new ApiException("No consultations found for this owner");
        }
        return consultations;
    }
    //shahad
    //list consultation for investor
    public List<Consultation> listConsultationsForInvestor(Integer investorId) {
        Investor investor = investorRepository.findInvestorById(investorId);
        if (investor == null) {
            throw new ApiException("Investor not found");
        }
        List<Consultation> consultations = consultationRepository.findConsultationByInvestorId(investorId);
        if (consultations.isEmpty()) {
            throw new ApiException("No consultations found for this investor");
        }
        return consultations;
    }

}
