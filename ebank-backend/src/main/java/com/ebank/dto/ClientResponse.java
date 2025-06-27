package com.ebank.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientResponse {
    
    private Long id;
    private String nom;
    private String prenom;
    private String numeroIdentite;
    private LocalDate dateNaissance;
    private String email;
    private String adressePostale;
    private String login;
    private LocalDateTime createdAt;
    private String message;
    
    // Default constructor
    public ClientResponse() {}
    
    // All-args constructor
    public ClientResponse(Long id, String nom, String prenom, String numeroIdentite, LocalDate dateNaissance, String email, String adressePostale, String login, LocalDateTime createdAt, String message) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroIdentite = numeroIdentite;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.adressePostale = adressePostale;
        this.login = login;
        this.createdAt = createdAt;
        this.message = message;
    }
    
    // Builder pattern
    public static ClientResponseBuilder builder() {
        return new ClientResponseBuilder();
    }
    
    public static class ClientResponseBuilder {
        private Long id;
        private String nom;
        private String prenom;
        private String numeroIdentite;
        private LocalDate dateNaissance;
        private String email;
        private String adressePostale;
        private String login;
        private LocalDateTime createdAt;
        private String message;
        
        public ClientResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public ClientResponseBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }
        
        public ClientResponseBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }
        
        public ClientResponseBuilder numeroIdentite(String numeroIdentite) {
            this.numeroIdentite = numeroIdentite;
            return this;
        }
        
        public ClientResponseBuilder dateNaissance(LocalDate dateNaissance) {
            this.dateNaissance = dateNaissance;
            return this;
        }
        
        public ClientResponseBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public ClientResponseBuilder adressePostale(String adressePostale) {
            this.adressePostale = adressePostale;
            return this;
        }
        
        public ClientResponseBuilder login(String login) {
            this.login = login;
            return this;
        }
        
        public ClientResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public ClientResponseBuilder message(String message) {
            this.message = message;
            return this;
        }
        
        public ClientResponse build() {
            return new ClientResponse(id, nom, prenom, numeroIdentite, dateNaissance, email, adressePostale, login, createdAt, message);
        }
    }
    
    // Getter methods
    public Long getId() {
        return id;
    }
    
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
    
    public String getLogin() {
        return login;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public String getMessage() {
        return message;
    }
    
    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
} 