package com.example.xamantei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xamantei.entity.CustomerLink;


public interface CustomLinkRepository extends JpaRepository<CustomerLink, Long> {
    CustomerLink findBytitle(String title);
    CustomerLink findByid(Long id);
} 
