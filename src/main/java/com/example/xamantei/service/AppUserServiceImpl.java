package com.example.xamantei.service;

import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.xamantei.dto.AddCustomLinksDto;
import com.example.xamantei.dto.AddSocialLinksDto;
import com.example.xamantei.dto.CreateUserDto;
import com.example.xamantei.entity.Appuser;
import com.example.xamantei.entity.CustomerLink;
import com.example.xamantei.entity.SocialLink;
import com.example.xamantei.repository.AppUserRepository;
import com.example.xamantei.repository.CustomLinkRepository;
import com.example.xamantei.repository.SocialLinkRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private AppUserRepository appUserRepository;
    private CustomLinkRepository customLinkRepository;
    private SocialLinkRepository SocialLinkRepository;
    
    @Override
    public Appuser addUser(String prenom, String nom, String username, String mail, String telephone){
        Appuser user = appUserRepository.findByUsername(username);
        
        appUserRepository.save(user);
        return user;
        
    }
    @Override
    public Appuser getUser(String username){
        Appuser user = appUserRepository.findByUsername(username);
        
        
        return user;
        
    }
    @Override
    public CustomerLink addCustomerLink(AddCustomLinksDto addCustomLinksDto, String username){
        System.out.println("addCustomerLink "+ username);
        System.out.println("addCustomerLink "+ addCustomLinksDto);
        CustomerLink links = new CustomerLink();
        Appuser user=appUserRepository.findByUsername(username);


        links.setAppUser(user);
        links.setOrderValue(addCustomLinksDto.getOrder());
        links.setTitle(addCustomLinksDto.getTitle());
        links.setPlatform(addCustomLinksDto.getPlatform());
        links.setUrl(addCustomLinksDto.getUrl());

        
        customLinkRepository.save(links);
        System.out.println( links);
        return links;
        
    }
    @Override
    public SocialLink addSocialLink(AddSocialLinksDto addSocialLinksDto, String username){
        SocialLink links = new SocialLink();
        Appuser user=appUserRepository.findByUsername(username);


        links.setAppUser(user);
        links.setOrderValue(addSocialLinksDto.getOrder());
        links.setTitle(addSocialLinksDto.getTitle());
        links.setPlatform(addSocialLinksDto.getPlatforme());
        links.setUrl(addSocialLinksDto.getUrl());
        
        SocialLinkRepository.save(links);
        return links;
        
    }
    

    @Override
    public Appuser saveUser(String username, String password, String fullName, String mail, String telephone, String bio ) {
        Appuser user=appUserRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("User already exists");
        // if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        Appuser appUser=new Appuser();
        appUser.setUsername(username);
        appUser.setEmail(mail);
        appUser.setFullName(fullName);
        appUser.setPassword(password);
        appUser.setTelephone(telephone);
        appUser.setBio(bio);
        appUserRepository.save(appUser);
        // addRoleToUser(username,"USER");
        // addRoleToUser(username,"ADMIN");
        return appUser;
    }

    @Override
    public Appuser updateAppUser(Long id, CreateUserDto userDto) {
        System.out.println("userDto");
        System.out.println(userDto);
        Optional<Appuser> user=appUserRepository.findById(id);
        System.out.println(user.get());
  
            user.get().setUsername(userDto.getUsername());
            user.get().setFullName(userDto.getFullName());
            user.get().setBio(userDto.getBio());
            System.out.println("user to save");
            System.out.println(user.get());
            return appUserRepository.save(user.get());
    
        // if(user!=null) throw new RuntimeException("User already exists");
        // // if(!password.equals(confirmedPassword)) throw new RuntimeException("Please confirm your password");
        // Appuser appUser=new Appuser();
        // appUser.setUsername(username);
        // appUser.setEmail(mail);
        // appUser.setFullName(fullName);
        // appUser.setPassword(password);
        // appUser.setTelephone(telephone);
        // appUser.setBio(bio);
        // appUserRepository.save(appUser);
        // // addRoleToUser(username,"USER");
        // // addRoleToUser(username,"ADMIN");
        // return appUser;
    }

    @Override
    public boolean deleteCustomLink(Long id){
        Optional<CustomerLink> optional = customLinkRepository.findById(id);
        
        if (optional.isPresent()) {
            System.out.println(optional.get());
            customLinkRepository.delete(optional.get());
            return true;
        }
        return false;
        
    }



}
