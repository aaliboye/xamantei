package com.example.xamantei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xamantei.dto.CommandeRequest;
import com.example.xamantei.entity.Commande;
import com.example.xamantei.service.CommandeService;

@RestController
@RequestMapping("/commandes")
public class CommandController {

    @Autowired
    private CommandeService commandeService;

    @PostMapping
    public ResponseEntity<?> passerCommande(@RequestBody CommandeRequest request) {
        try {
            Commande commande = commandeService.creerCommande(request);
            return ResponseEntity.ok(commande);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
