package com.example.xamantei.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.xamantei.entity.Produit;
import com.example.xamantei.repository.ProduitRepository;
import com.example.xamantei.service.ProduitService;

@RestController
@RequestMapping("/produit")
public class ProduitController {

     @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

    @PostMapping
    public ResponseEntity<Produit> ajouterProduit(@RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.creerProduit(produit));
    }

    @PostMapping("/{id}/commander")
    public ResponseEntity<?> commander(@PathVariable Long id, @RequestParam int quantite) {
        try {
            Produit produit = produitService.commanderProduit(id, quantite);
            return ResponseEntity.ok(produit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Produit> lister() {
        return produitService.listerProduits();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> uploadProduitAvecImage(
        @RequestParam("nom") String nom,
        @RequestParam("prix") double prix,
        @RequestParam("stock") int stock,
        @RequestParam("image") MultipartFile image) throws IOException {

    // Sauvegarder l'image dans un dossier local ou cloud
    String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
    Path path = Paths.get("uploads/" + fileName);
    Files.createDirectories(path.getParent());
    Files.write(path, image.getBytes());

    // Créer le produit
    Produit produit = new Produit();
    produit.setNom(nom);
    produit.setPrix(prix);
    produit.setStock(stock);
    produit.setDisponible(stock > 0);
    produit.setImage("/uploads/" + fileName); // chemin relatif ou URL

    return ResponseEntity.ok(produitRepository.save(produit));
}

    
}
