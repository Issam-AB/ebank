package com.ebank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class VirementRequest {
    
    @NotBlank(message = "Le RIB source est obligatoire")
    private String ribSource;
    
    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;
    
    @NotBlank(message = "Le RIB destinataire est obligatoire")
    private String ribDestination;
    
    @NotBlank(message = "Le motif est obligatoire")
    private String motif;
    
    // Explicit getter methods for Lombok compatibility
    public String getRibSource() {
        return ribSource;
    }
    
    public BigDecimal getMontant() {
        return montant;
    }
    
    public String getRibDestination() {
        return ribDestination;
    }
    
    public String getMotif() {
        return motif;
    }
    
    // Additional setter methods
    public void setRibSource(String ribSource) {
        this.ribSource = ribSource;
    }
    
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
    public void setRibDestination(String ribDestination) {
        this.ribDestination = ribDestination;
    }
    
    public void setMotif(String motif) {
        this.motif = motif;
    }
} 