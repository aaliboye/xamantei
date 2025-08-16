package com.example.xamantei.service;

import org.springframework.stereotype.Service;

import com.example.xamantei.entity.AppRole;
import com.example.xamantei.repository.AppRoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppRoleServiceImpl implements AppRoleService {

    private AppRoleRepository appRoleRepository;
    
    @Override
    public AppRole getRoleByName(String name){
        return appRoleRepository.getRoleByRoleName(name);
    }
}
