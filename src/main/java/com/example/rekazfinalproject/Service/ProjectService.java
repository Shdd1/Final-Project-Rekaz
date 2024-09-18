package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor


public class ProjectService {

    private final ProjectRepository projectRepository;
    private final BidRepository bidRepository;
    private final ContractRepository contractRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    // Suliman
    public List<Project> getAllProjects() {
        List<Project> allProjects = new ArrayList<>();
        for (Project project : projectRepository.findAll() ) {
            if(project.getOwner().getSubscription()!=null){
                allProjects.add(project);
            }
        }
        for (Project project : projectRepository.findAll() ) {
            if(project.getOwner().getSubscription()==null){
                allProjects.add(project);
            }
        }
        return allProjects;

    }


    // Suliman
  public void addProject( Integer ownerId , Integer propertyId , Project project) {
      Owner owner = ownerRepository.findOwnerById(ownerId);

      Property property = propertyRepository.findPropertyById(propertyId);
      if(property==null){
          throw new ApiException("property not found");
      }

      if(!owner.getUser().isActive()){
          throw new ApiException("User is not active");
      }
      project.setOwner(owner);
      project.setStatus(Project.ProjectStatus.PENDING);
      project.setProperty(property);
      projectRepository.save(project);
  }

    // Suliman
    public void updateProject( Integer ownerId , Integer projectId , Project project) {
        Project project1 = projectRepository.findProjectById(projectId);
        if (project1 == null) {
            throw new ApiException("Project not found");
        }

        User user = userRepository.findUserById(ownerId);
        if(project1.getOwner().getId() != user.getId()){
            throw new ApiException("User id mismatch");
        }

        project1.setTitle(project.getTitle());
        project1.setDescription(project.getDescription());
        project1.setProjectType(project.getProjectType());
        project1.setBudget(project.getBudget());
        project1.setDeadline(project.getDeadline());
        project1.setCity(project.getCity());
        projectRepository.save(project1);
    }


    // Suliman
    public void deleteProject( Integer ownerId , Integer projectId ) {

        Project project1 = projectRepository.findProjectById(projectId);
        if (project1 == null) {
            throw new ApiException("Project not found");
        }

        User user = userRepository.findUserById(ownerId);
        if(project1.getOwner().getId() != user.getId()){
            throw new ApiException("User id mismatch");
        }

        for (Contract contract : contractRepository.findAll() ) {
            if (contract.getProject().getId() == projectId) {
                throw new ApiException("Project is associated with this investor company you cannot delete this project");
            }
        }


        Project project = projectRepository.findProjectById(projectId);
        if (project == null) {
            throw new ApiException("Project not found");
        }

        projectRepository.delete(project);
    }

    //Danah
    public List<Project> findByCity(String city) {
        return projectRepository.findProjectByCity(city);
    }

    //Danah
    public List<Project> findByProjectType(String projectType) {
        return projectRepository.findProjectByProjectType(projectType);
    }

    // Suliman

    public List<Project> getOwnerProjects(Integer ownerId) {
        List<Project> ownerProjects = new ArrayList<>();
        Owner owner = ownerRepository.findOwnerById(ownerId);

        for(Project project : projectRepository.findAll()){
            if (project.getOwner().equals(owner)) {
                ownerProjects.add(project);
            }
        }
        if (ownerProjects.isEmpty()) {
            throw new ApiException("Owner doesn't have any projects");
        }
        return ownerProjects;
    }

    // Suliman

    public List<Project> getClosestProjects(){
        List<Project> closestProject = projectRepository.findAll();

        for (int i = 0; i < closestProject.size() - 1; i++) {
            for (int j = 0; j < closestProject.size() - i - 1; j++) {
                if (closestProject.get(j).getStartDate().isAfter(closestProject.get(j + 1).getStartDate()) || closestProject.get(j).getStartDate().isEqual(closestProject.get(j + 1).getStartDate())) {
                    Project closest = closestProject.get(j);
                    closestProject.set(j, closestProject.get(j + 1));
                    closestProject.set(j + 1, closest);
                }
            }
        }
        if(closestProject.isEmpty()){
            throw new ApiException("No project found");
        }
        return closestProject;
    }

    // Suliman

    public List<Project> getHighestProjectsBudget(){
        List<Project> highestProjects = projectRepository.findAll();

        for (int i = 0; i < highestProjects.size() - 1; i++) {
            for (int j = 0; j < highestProjects.size() - i - 1; j++) {
                if (highestProjects.get(j).getBudget() <= highestProjects.get(j + 1).getBudget()) {
                    Project highest = highestProjects.get(j);
                    highestProjects.set(j, highestProjects.get(j + 1));
                    highestProjects.set(j + 1, highest);
                }
            }
        }
        if(highestProjects.isEmpty()){
            throw new ApiException("No project found");
        }
        return highestProjects;
    }

// Suliman


    public List<Project> getProjectByDate(LocalDate startDate , LocalDate endDate){
        if (startDate.isAfter(endDate)) {
            throw new ApiException("Start date cannot be after end date");
        }
        if(projectRepository.findByProjectDateBetween(startDate, endDate).isEmpty()){
            throw new ApiException("No project found in this date");
        }
        return projectRepository.findByProjectDateBetween(startDate , endDate);
    }
    //Shahad
    // investor get list project by budget
    public List<Project> getProjectsByBudget( int investorId , double maxBudget) {
        User invstorUser=userRepository.findUserById(investorId);
        if(invstorUser==null||!invstorUser.getRole().equalsIgnoreCase("Investor")){
            throw new ApiException("Investor not found");
        }
        if(!invstorUser.isActive()){
            throw new ApiException("Investor not Active");
        }
        return projectRepository.listProjectWithSpecvicedBudgetAndStatus(maxBudget);
    }
    

}
