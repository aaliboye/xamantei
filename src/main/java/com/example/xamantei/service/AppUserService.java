package com.example.xamantei.service;

import org.springframework.http.ResponseEntity;

import com.example.xamantei.dto.AddCustomLinksDto;
import com.example.xamantei.dto.AddSocialLinksDto;
import com.example.xamantei.dto.CreateUserDto;
import com.example.xamantei.entity.Appuser;
import com.example.xamantei.entity.CustomerLink;
import com.example.xamantei.entity.SocialLink;

public interface AppUserService {

    public Appuser addUser(String prenom, String nom, String username, String mail, String telephone);
    public Appuser saveUser(String username, String password, String fullName, String mail, String telephone, String bio);
    public Appuser updateAppUser(Long id,CreateUserDto userDto);
    public Appuser getUser(String username);
    public CustomerLink addCustomerLink(AddCustomLinksDto addCustomLinksDto, String username);
    public SocialLink addSocialLink(AddSocialLinksDto addSocialLinksDto, String username);
    public boolean deleteCustomLink(Long id);
    // public Appuser login(String username, String password);
    
} 
