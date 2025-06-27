package com.ebank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ClientRequest {
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    @NotBlank(message = "Le numéro d'identité est obligatoire")
    private String numeroIdentite;
    
    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;
    
    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String email;
    
    @NotBlank(message = "L'adresse postale est obligatoire")
    private String adressePostale;
    
    // Default constructor
    public ClientRequest() {}
    
    // All-args constructor
    public ClientRequest(String nom, String prenom, String numeroIdentite, LocalDate dateNaissance, String email, String adressePostale) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroIdentite = numeroIdentite;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.adressePostale = adressePostale;
    }
    
    // Getter methods
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getNumeroIdentite() {
        return numeroIdentite;
    }
    
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAdressePostale() {
        return adressePostale;
    }
    
    // Setter methods
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public void setNumeroIdentite(String numeroIdentite) {
        this.numeroIdentite = numeroIdentite;
    }
    
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }
} 