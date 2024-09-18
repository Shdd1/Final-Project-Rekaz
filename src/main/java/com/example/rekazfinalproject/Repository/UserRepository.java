package com.example.rekazfinalproject.Repository;

import com.example.rekazfinalproject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(int id);

    User findUserByUsername(String username);

}
