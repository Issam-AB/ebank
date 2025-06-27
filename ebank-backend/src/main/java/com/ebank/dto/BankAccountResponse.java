package com.ebank.dto;

import com.ebank.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankAccountResponse {
    
    private Long id;
    private String rib;
    private BigDecimal solde;
    private AccountStatus statut;
    private String clientNom;
    private String clientPrenom;
    private LocalDateTime lastActivityAt;
    private LocalDateTime createdAt;
    
    // Default constructor
    public BankAccountResponse() {}
    
    // All-args constructor
    public BankAccountResponse(Long id, String rib, BigDecimal solde, AccountStatus statut, String clientNom, String clientPrenom, LocalDateTime lastActivityAt, LocalDateTime createdAt) {
        this.id = id;
        this.rib = rib;
        this.solde = solde;
        this.statut = statut;
        this.clientNom = clientNom;
        this.clientPrenom = clientPrenom;
        this.lastActivityAt = lastActivityAt;
        this.createdAt = createdAt;
    }
    
    // Builder pattern
    public static BankAccountResponseBuilder builder() {
        return new BankAccountResponseBuilder();
    }
    
    public static class BankAccountResponseBuilder {
        private Long id;
        private String rib;
        private BigDecimal solde;
        private AccountStatus statut;
        private String clientNom;
        private String clientPrenom;
        private LocalDateTime lastActivityAt;
        private LocalDateTime createdAt;
        
        public BankAccountResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public BankAccountResponseBuilder rib(String rib) {
            this.rib = rib;
            return this;
        }
        
        public BankAccountResponseBuilder solde(BigDecimal solde) {
            this.solde = solde;
            return this;
        }
        
        public BankAccountResponseBuilder statut(AccountStatus statut) {
            this.statut = statut;
            return this;
        }
        
        public BankAccountResponseBuilder clientNom(String clientNom) {
            this.clientNom = clientNom;
            return this;
        }
        
        public BankAccountResponseBuilder clientPrenom(String clientPrenom) {
            this.clientPrenom = clientPrenom;
            return this;
        }
        
        public BankAccountResponseBuilder lastActivityAt(LocalDateTime lastActivityAt) {
            this.lastActivityAt = lastActivityAt;
            return this;
        }
        
        public BankAccountResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public BankAccountResponse build() {
            return new BankAccountResponse(id, rib, solde, statut, clientNom, clientPrenom, lastActivityAt, createdAt);
        }
    }
    
    // Getter methods
    public Long getId() {
        return id;
    }
    
    public String getRib() {
        return rib;
    }
    
    public BigDecimal getSolde() {
        return solde;
    }
    
    public AccountStatus getStatut() {
        return statut;
    }
    
    public String getClientNom() {
        return clientNom;
    }
    
    public String getClientPrenom() {
        return clientPrenom;
    }
    
    public LocalDateTime getLastActivityAt() {
        return lastActivityAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setRib(String rib) {
        this.rib = rib;
    }
    
    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }
    
    public void setStatut(AccountStatus statut) {
        this.statut = statut;
    }
    
    public void setClientNom(String clientNom) {
        this.clientNom = clientNom;
    }
    
    public void setClientPrenom(String clientPrenom) {
        this.clientPrenom = clientPrenom;
    }
    
    public void setLastActivityAt(LocalDateTime lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 