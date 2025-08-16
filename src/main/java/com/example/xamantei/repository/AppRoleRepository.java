package com.example.xamantei.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.xamantei.entity.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {

    AppRole getRoleByRoleName(String roleName);
} 
