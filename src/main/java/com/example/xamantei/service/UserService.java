package com.example.xamantei.service;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.xamantei.entity.AppRole;
import com.example.xamantei.entity.Appuser;
import com.example.xamantei.repository.AppUserRepository;

@Service
public class UserService implements UserDetailsService {

    private AppUserRepository appUserRepository;

    public UserService(AppUserRepository userRepository) {
        this.appUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Appuser user = appUserRepository.findByUsername(username);

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                                .map(role -> role.getRoleName().toUpperCase())
                                .collect(Collectors.toList())
                                .toArray(new String[0]))// Convertit en tableau
                .build();




    }

    
    
}
