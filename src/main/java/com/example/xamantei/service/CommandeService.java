package com.example.xamantei.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xamantei.dto.CommandeRequest;
import com.example.xamantei.dto.LigneCommandeRequest;
import com.example.xamantei.entity.Appuser;
import com.example.xamantei.entity.Commande;
import com.example.xamantei.entity.LigneCommande;
import com.example.xamantei.entity.Produit;
import com.example.xamantei.repository.AppUserRepository;
import com.example.xamantei.repository.CommandeRepository;
import com.example.xamantei.repository.ProduitRepository;

@Service
public class CommandeService {
    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeRepository commandeRepository;



    @Autowired
    private AppUserRepository userRepository;

public Commande creerCommande(CommandeRequest request) throws Exception {
    Appuser user = userRepository.findById(request.getUserId())
        .orElseThrow(() -> new Exception("Utilisateur non trouvé"));

    List<LigneCommande> lignes = new ArrayList<>();

    for (LigneCommandeRequest ligneReq : request.getLignes()) {
        Produit produit = produitRepository.findById(ligneReq.getProduitId())
            .orElseThrow(() -> new Exception("Produit non trouvé : " + ligneReq.getProduitId()));

        if (produit.getStock() < ligneReq.getQuantite()) {
            throw new Exception("Stock insuffisant pour : " + produit.getNom());
        }

        produit.setStock(produit.getStock() - ligneReq.getQuantite());
        produit.setDisponible(produit.getStock() > 0);
        produitRepository.save(produit);

        LigneCommande ligne = new LigneCommande();
        ligne.setProduit(produit);
        ligne.setQuantite(ligneReq.getQuantite());
        ligne.setPrixUnitaire(produit.getPrix());
        lignes.add(ligne);
    }

    Commande commande = new Commande();
    commande.setAppUser(user);
    commande.setDateCommande(LocalDateTime.now());
    commande.setLignes(lignes);

    return commandeRepository.save(commande);
}

}
