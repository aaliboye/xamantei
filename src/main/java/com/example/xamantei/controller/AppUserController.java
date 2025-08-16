package com.example.xamantei.controller;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.xamantei.dto.AddCustomLinksDto;
import com.example.xamantei.dto.AddSocialLinksDto;
import com.example.xamantei.dto.CreateUserDto;
import com.example.xamantei.dto.LoginUserDto;
import com.example.xamantei.entity.Appuser;
import com.example.xamantei.entity.CustomerLink;
import com.example.xamantei.entity.SocialLink;
import com.example.xamantei.repository.AppRoleRepository;
import com.example.xamantei.repository.AppUserRepository;
import com.example.xamantei.security.JwtUtils;
import com.example.xamantei.service.AppUserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;

@RestController
public class AppUserController {
    private AppUserService appUserService ;
    private PasswordEncoder passwordEncoder;
     private  AuthenticationManager authenticationManager;
    private  UserDetailsService userDetailsService;
    private  JwtUtils jwtUtil;


    public AppUserController(AppUserService appUserService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils){
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtils;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, authenticated user!");
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        System.out.println("get user");
        Appuser user = appUserService.getUser(username);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé : " + username);
        }
        return ResponseEntity.ok(user);
    }


    
    @PostMapping("/auth/register")
    public ResponseEntity<?> savUser(@RequestBody CreateUserDto userForm){
    
        try {
            Appuser appuserr = appUserService.getUser(userForm.getUsername());
            if(appuserr == null){

                Appuser appUser = appUserService.saveUser(
                    userForm.getUsername(),
                    passwordEncoder.encode(userForm.getPassword()),
                    userForm.getFullName(),
                    userForm.getEmail(),
                    userForm.getTelephone(),
                    ""
                );
        
                return ResponseEntity.status(HttpStatus.CREATED).body(appUser);
            }
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "user already exist"));
    
        }  catch (Exception ex) {
            // Erreur générale (ex: problème de BDD)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Erreur serveur : " + ex.getMessage()));
        }
        
        // Appuser appUser = appUserService.saveUser(userForm.getUsername(),passwordEncoder.encode(userForm.getPassword()), userForm.getFullName(), userForm.getEmail(), userForm.getTelephone(), "");
        // System.out.println("appUser");
        // System.out.println(appUser);
        // return appUser;
    }
    
    
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody CreateUserDto userForm, @PathVariable Long id){
        System.out.println(id);
    
        try {
            
            Appuser appuserr = appUserService.updateAppUser(id, userForm);

            System.out.println("appuserr");
            System.out.println(appuserr);
            if(appuserr != null){

                
        
                return ResponseEntity.ok(appuserr);
            }
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "user not found"));
    
        }  catch (DataIntegrityViolationException ex) {
            System.out.println("conflit resp");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "username existe deja"));
        }
        catch (Exception ex) {
            // Erreur générale (ex: problème de BDD)

            System.out.println(ex);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("error", "Erreur serveur : " + ex.getMessage()));
        }
        
        // Appuser appUser = appUserService.saveUser(userForm.getUsername(),passwordEncoder.encode(userForm.getPassword()), userForm.getFullName(), userForm.getEmail(), userForm.getTelephone(), "");
        // System.out.println("appUser");
        // System.out.println(appUser);
        // return appUser;
    }
    

    @PostMapping("/addCustomLinks/{username}")
    public ResponseEntity<CustomerLink> addCustomLinks(@PathVariable String username, @RequestBody AddCustomLinksDto addCustomLinksDto){
        System.out.println("username "+ username);
        CustomerLink customerLink = appUserService.addCustomerLink(addCustomLinksDto, username);

        System.out.println("username "+ customerLink);

        return ResponseEntity.status(HttpStatus.CREATED).body(customerLink);

        // return customerLink;
    }

    @PostMapping("/addSocialLinks/{username}")
    public SocialLink addSocialLinks(@PathVariable String username, @RequestBody AddSocialLinksDto addSocialLinksDto){
        System.out.println("username "+ username);
        SocialLink socialLink = appUserService.addSocialLink(addSocialLinksDto, username);
        return socialLink;
    }

    @DeleteMapping("/deleteLink/{linkId}")
    public ResponseEntity<String> deleteCustomLink(@PathVariable Long linkId) {
        boolean deleted = appUserService.deleteCustomLink(linkId);
        if (deleted) {
            return ResponseEntity.ok("Lien supprimé avec succès.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lien non trouvé.");
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.get("username"), request.get("password")
        ));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.get("username"));
        Appuser appuser = appUserService.getUser(userDetails.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(Map.of("token", token, "user", appuser));
    }
}


