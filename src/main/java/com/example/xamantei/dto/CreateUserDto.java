package com.example.xamantei.dto;

import lombok.Data;

@Data
public class CreateUserDto {

    private String username;
    private String password;
    private String fullName;
    private String telephone;
    private String email;
    private String bio;
    private String confirmedPassword;
    
}