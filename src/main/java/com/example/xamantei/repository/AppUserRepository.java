package com.example.xamantei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xamantei.entity.Appuser;

public interface AppUserRepository extends JpaRepository<Appuser, Long> {

    Appuser findByUsername(String username);
    
}
