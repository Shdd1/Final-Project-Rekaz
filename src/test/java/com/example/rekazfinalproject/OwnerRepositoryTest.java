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


@ExtendWith(SpringExtension.class)
@DataJpaTest //test repo
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OwnerRepositoryTest {


    @Autowired
    UserRepository userRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    User user;


    Owner owner1 , owner2;


    @BeforeEach
    public void setUp() {

        user = new User(null, "username", "Ss123456", "mail@mail.com", "OWNER", true, null, null, null);

        owner1  = new Owner(null,123,"Small",1, LocalDate.now(),0,null,null,user,null,null,null,null,null,null);


    }

    //test method one in repo
    @Test
    public void findOwnerById() {
        userRepository.save(user);
        ownerRepository.save(owner1);
        owner2 = ownerRepository.findOwnerById(owner1.getId());
        Assertions.assertThat(owner1).isEqualTo(owner2);
    }

}