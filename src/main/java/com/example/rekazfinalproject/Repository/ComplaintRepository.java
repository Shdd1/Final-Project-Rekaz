package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Complaint;
import jakarta.validation.constraints.Max;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    Complaint findComplaintsById(Integer id);
    @Query("SELECT c FROM Complaint c WHERE c.priority_level = 'Normal'")
    List<Complaint> findNormalComplaints();
    @Query("SELECT c FROM Complaint c WHERE c.priority_level = 'Urgent'")
    List<Complaint>findUrgentComplaints ();


}