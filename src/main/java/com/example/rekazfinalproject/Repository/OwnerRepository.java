package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Owner findOwnerById(Integer id);
    Owner findOwnerByPropertiesContaining(Property property);

}
