package com.example.xamantei.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.xamantei.entity.Produit;
import com.example.xamantei.repository.ProduitRepository;

@Service
public class ProduitService {
    
     @Autowired
    private ProduitRepository produitRepository;

    public Produit creerProduit(Produit produit) {
        produit.setDisponible(produit.getStock() > 0);
        return produitRepository.save(produit);
    }

    public Produit commanderProduit(Long idProduit, int quantite) throws Exception {
        Produit produit = produitRepository.findById(idProduit)
            .orElseThrow(() -> new Exception("Produit non trouvé"));

        if (produit.getStock() < quantite) {
            throw new Exception("Stock insuffisant");
        }

        produit.setStock(produit.getStock() - quantite);
        produit.setDisponible(produit.getStock() > 0);
        return produitRepository.save(produit);
    }

    public List<Produit> listerProduits() {
        return produitRepository.findAll();
    }
}
