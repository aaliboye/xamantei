package com.example.xamantei.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommandeRequest {
    private Long userId;
    private List<LigneCommandeRequest> lignes;
}


