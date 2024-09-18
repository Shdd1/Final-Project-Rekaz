package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    Subscription findSubscriptionById(int id);
}
