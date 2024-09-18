package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BidRepository extends JpaRepository<Bid,Integer> {

    Bid findBidById(Integer id);
}
