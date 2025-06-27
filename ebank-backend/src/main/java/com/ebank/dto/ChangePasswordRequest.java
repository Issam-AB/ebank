package com.ebank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    
    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    private String ancienMotDePasse;
    
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 6, message = "Le nouveau mot de passe doit contenir au moins 6 caractères")
    private String nouveauMotDePasse;
    
    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmationMotDePasse;
    
    // Default constructor
    public ChangePasswordRequest() {}
    
    // All-args constructor
    public ChangePasswordRequest(String ancienMotDePasse, String nouveauMotDePasse, String confirmationMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
        this.confirmationMotDePasse = confirmationMotDePasse;
    }
    
    // Getter methods
    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }
    
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }
    
    public String getConfirmationMotDePasse() {
        return confirmationMotDePasse;
    }
    
    // Setter methods
    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }
    
    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
    
    public void setConfirmationMotDePasse(String confirmationMotDePasse) {
        this.confirmationMotDePasse = confirmationMotDePasse;
    }
} 