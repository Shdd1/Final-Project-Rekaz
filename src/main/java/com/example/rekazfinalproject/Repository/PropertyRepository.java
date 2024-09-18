package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Property;
import com.example.rekazfinalproject.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Integer> {
    Property findPropertyById(Integer id);
    List<Property> findAllByOwner(Owner owner);
}
