package com.ebank.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "Le login est obligatoire")
    private String login;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
    
    // Default constructor
    public LoginRequest() {}
    
    // All-args constructor
    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    // Getter methods
    public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }
    
    // Setter methods
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 