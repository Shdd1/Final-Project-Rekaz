package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.DTO.OwnerDTO;
import com.example.rekazfinalproject.DTO.PropertyDTO;
import com.example.rekazfinalproject.Model.Owner;
import com.example.rekazfinalproject.Model.Project;
import com.example.rekazfinalproject.Model.Property;
import com.example.rekazfinalproject.Model.User;
import com.example.rekazfinalproject.Repository.OwnerRepository;
import com.example.rekazfinalproject.Repository.ProjectRepository;
import com.example.rekazfinalproject.Repository.PropertyRepository;
import com.example.rekazfinalproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final ProjectRepository projectRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    public List<Property> getMyProperty(Integer ownerId) {
        Owner owner=ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("Owner not found");
        }
        return propertyRepository.findAllByOwner(owner);
    }

    public void addProperty(Integer ownerId,PropertyDTO propertyDTO) {
        // Create and populate Project entity
        Owner owner=ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("Owner not found");
        }

        User ownerUser = userRepository.findUserById(ownerId);
        if(ownerUser.isActive()==false){
            throw new ApiException("owner is not active");
        }


        // Create and populate property entity
        Property property=new Property();
        property.setLocation(propertyDTO.getLocation());
        property.setLandTitle(propertyDTO.getLandTitle());
        property.setLandArea(propertyDTO.getLandArea());
        property.setDescription(propertyDTO.getDescription());



        property.setOwner(owner);
        propertyRepository.save(property);
    }

    public void updateProperty(Integer ownerId,Integer propertyId, PropertyDTO propertyDTO) {
        Owner owner=ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("User not found");
        }
        Property property = propertyRepository.findPropertyById(propertyId);
        if (property == null) {
            throw new ApiException("property not found");
        }
        if(!owner.getId().equals(property.getOwner().getId())){
            throw new ApiException("You do not have the authority");
        }

        Project project=new Project();
        project.setDescription(propertyDTO.getDescription());
        projectRepository.save(project);


        property.setLocation(propertyDTO.getLocation());
        property.setLandTitle(propertyDTO.getLandTitle());
        property.setLandArea(propertyDTO.getLandArea());
        property.setDescription(propertyDTO.getDescription());
        propertyRepository.save(property);



    }

    public void deleteProperty(Integer ownerId,Integer id) {
        Owner owner=ownerRepository.findOwnerById(ownerId);
        if(owner==null){
            throw new ApiException("User not found");
        }
        Property property = propertyRepository.findPropertyById(id);
        if (property == null) {
            throw new ApiException("property not found");
        }
        if(!owner.getId().equals(property.getOwner().getId())){
            throw new ApiException("You do not have the authority");
        }
        propertyRepository.delete(property);
    }

}
