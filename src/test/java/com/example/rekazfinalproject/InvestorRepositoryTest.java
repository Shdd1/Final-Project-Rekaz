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
public class InvestorRepositoryTest {


    @Autowired
    UserRepository userRepository;
    @Autowired
    private InvestorRepository investorRepository;

    User user;


    Investor investor1 , investor2;






    @BeforeEach
    public void setUp() {
        user = new User(null, "username", "Ss123456", "mail@mail.com", "OWNER", true, null, null, null);

        investor1 = new Investor(null, 123, 10 , "Small", LocalDate.now(), 0,user,null,null,null,null,null,null,null);


    }

    //test method one in repo
    @Test
    public void findOwnerById() {
        userRepository.save(user);
        investorRepository.save(investor1);
        investor2 = investorRepository.findInvestorById(investor1.getId());
        Assertions.assertThat(investor1).isEqualTo(investor2);
    }
}