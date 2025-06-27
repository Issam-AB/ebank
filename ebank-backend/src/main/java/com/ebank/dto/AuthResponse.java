package com.ebank.dto;

import com.ebank.enums.UserRole;

public class AuthResponse {
    
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String login;
    private String nom;
    private String prenom;
    private UserRole role;
    private long expiresIn; // in milliseconds
    
    // Default constructor
    public AuthResponse() {}
    
    // All-args constructor
    public AuthResponse(String token, String tokenType, Long userId, String login, String nom, String prenom, UserRole role, long expiresIn) {
        this.token = token;
        this.tokenType = tokenType;
        this.userId = userId;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        this.expiresIn = expiresIn;
    }
    
    // Builder pattern
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }
    
    public static class AuthResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private Long userId;
        private String login;
        private String nom;
        private String prenom;
        private UserRole role;
        private long expiresIn;
        
        public AuthResponseBuilder token(String token) {
            this.token = token;
            return this;
        }
        
        public AuthResponseBuilder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }
        
        public AuthResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        
        public AuthResponseBuilder login(String login) {
            this.login = login;
            return this;
        }
        
        public AuthResponseBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }
        
        public AuthResponseBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }
        
        public AuthResponseBuilder role(UserRole role) {
            this.role = role;
            return this;
        }
        
        public AuthResponseBuilder expiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }
        
        public AuthResponse build() {
            return new AuthResponse(token, tokenType, userId, login, nom, prenom, role, expiresIn);
        }
    }
    
    // Getter methods
    public String getToken() {
        return token;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public String getLogin() {
        return login;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public long getExpiresIn() {
        return expiresIn;
    }
    
    // Setter methods
    public void setToken(String token) {
        this.token = token;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
} 