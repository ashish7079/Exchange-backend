package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;
import com.example.demo.Model.Auth;
import com.example.demo.repo.AuthRepo;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private AuthRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

        Auth login = repo.findByEmail(email)
        		 .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (login == null) {
            throw new UsernameNotFoundException("User not found");
        }
    
        return User.builder()
        		.username(login.getEmail())
        		.password(login.getPassword())
        		.build(); 
        
}
}
