package com.example.rekazfinalproject.Service;

import com.example.rekazfinalproject.Repository.UserRepository;
import com.example.rekazfinalproject.Api.ApiException;
import com.example.rekazfinalproject.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new ApiException("Wrong username or password");
        }

        return user;
    }

}
