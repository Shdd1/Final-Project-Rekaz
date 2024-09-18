package com.example.rekazfinalproject;

import com.example.rekazfinalproject.Model.*;
import com.example.rekazfinalproject.Repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@DataJpaTest //test repo
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    User user  ;

    Owner owner;

    Property property ;

    Project project1 , project2 ;




    @BeforeEach
    public void setUp() {

        user   = new User(null,"username","Ss123456","mail@mail.com","OWNER",true,null,null,null);

        owner  = new Owner(null,123,"Small",1, LocalDate.now(),0,null,null,user,null,null,null,null,null,null);


        property = new Property(null,"prop","Riyadh",100,200,"Nawaf","Property",owner,null,null);



        project1 =new Project(null,"project1","project 1",1000,"Residntial",LocalDate.now(),LocalDate.now().plusMonths(1), Project.ProjectStatus.IN_PROGRESS,"Riyadh",owner,null,null,null,null,property);




    }

    //test method one in repo
    @Test
    public void findProjectById() {
        userRepository.save(user);
        ownerRepository.save(owner);
        projectRepository.save(project1);
        project2=projectRepository.findProjectById(project1.getId());
        Assertions.assertThat(project1).isEqualTo(project1);
    }


    //test method one in repo
    @Test
    public void findProjectByOwnerId() {
        userRepository.save(user);
        ownerRepository.save(owner);
        projectRepository.save(project1);
        project2 = projectRepository.findProjectByOwnerId(project1.getOwner().getId());
        Assertions.assertThat(project2).isEqualTo(project1);
    }













}