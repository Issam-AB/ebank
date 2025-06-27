package com.ebank.dto;

import jakarta.validation.constraints.NotBlank;

public class BankAccountRequest {
    
    @NotBlank(message = "Le RIB est obligatoire")
    private String rib;
    
    @NotBlank(message = "Le numéro d'identité du client est obligatoire")
    private String numeroIdentiteClient;
    
    // Default constructor
    public BankAccountRequest() {}
    
    // All-args constructor
    public BankAccountRequest(String rib, String numeroIdentiteClient) {
        this.rib = rib;
        this.numeroIdentiteClient = numeroIdentiteClient;
    }
    
    // Getter methods
    public String getRib() {
        return rib;
    }
    
    public String getNumeroIdentiteClient() {
        return numeroIdentiteClient;
    }
    
    // Setter methods
    public void setRib(String rib) {
        this.rib = rib;
    }
    
    public void setNumeroIdentiteClient(String numeroIdentiteClient) {
        this.numeroIdentiteClient = numeroIdentiteClient;
    }
} 