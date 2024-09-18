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


@ExtendWith(SpringExtension.class)
@DataJpaTest //test repo
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {


    @Autowired
    UserRepository userRepository;

    User user1 , user2;



    @BeforeEach
    public void setUp() {

        user1 = new User(null, "user1", "pass1234", "mail@mail.com", "ADMIN", true, null, null, null);

    }

    //test method one in repo
    @Test
    public void findUserById() {
        userRepository.save(user1);
        user2 = userRepository.findUserById(user1.getId());
        Assertions.assertThat(user1).isEqualTo(user2);
    }

    @Test
    public void findUserByUsername() {
        userRepository.save(user1);
        user2 = userRepository.findUserByUsername(user1.getUsername());
        Assertions.assertThat(user1).isEqualTo(user2);
    }

}