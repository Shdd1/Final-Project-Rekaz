package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.SubscriptionDTO;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Subscription;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.SubscriptionRepository;
import com.example.rekazfinalproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor


public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    // CRUD by suliman

    public List<Subscription> getAllSubscriptions(){
        return subscriptionRepository.findAll();
    }

    public void addSubscription( int ownerId , SubscriptionDTO subscriptionDTO) {

        Owner owner = ownerRepository.findOwnerById(ownerId);
        if(owner == null) {
            throw new ApiException("owner not found");
        }
        User ownerUser = userRepository.findUserById(ownerId);
        if(!ownerUser.isActive()){
            throw new ApiException("owner is not active");
        }
        if(owner.getSubscription()!=null){
            throw new ApiException("owner already has subscription");
        }

        if(subscriptionDTO.getDuration()!=1 && subscriptionDTO.getDuration()!=3 && subscriptionDTO.getDuration()!=6 ){
            throw new ApiException("subscription duration not valid");
        }

        int price = 0 ;

        if(subscriptionDTO.getDuration()==1){
            price = 99 ;
        }

        if(subscriptionDTO.getDuration()==3){
            price = 149 ;
        }

        if(subscriptionDTO.getDuration()==6){
            price = 249 ;
        }




        Subscription subscription = new Subscription(subscriptionDTO.getOwnerId(),subscriptionDTO.getDuration(),price,Subscription.SubscriptionStatus.VALID, LocalDate.now(),LocalDate.now().plusMonths(subscriptionDTO.getDuration()),owner);
        subscription.setOwner(owner);
        subscriptionRepository.save(subscription);
    }

    public void updateSubscription( int id , Subscription subscription) {
        Subscription oldSubscription = subscriptionRepository.findSubscriptionById(id);
        if(oldSubscription == null) {
            throw new ApiException("subscription not found");
        }
        oldSubscription.setDuration(subscription.getDuration());
        // oldSubscription.setStartDate(subscription.getStartDate());
        // oldSubscription.setEndDate(subscription.getEndDate());
        subscriptionRepository.save(oldSubscription);
    }

    public void deleteSubscription( int id ) {
        Subscription subscription = subscriptionRepository.findSubscriptionById(id);
        if(subscription == null) {
            throw new ApiException("subscription not found");
        }
        if(subscription.getStatus() == Subscription.SubscriptionStatus.VALID){
            throw new ApiException("valid subscription cannot be deleted");
        }
        for(Owner owner : ownerRepository.findAll()){
            if(owner.getSubscription()==subscription){
                owner.setSubscription(null);
                ownerRepository.save(owner);
            }
        }
        subscriptionRepository.delete(subscription);
    }


}
