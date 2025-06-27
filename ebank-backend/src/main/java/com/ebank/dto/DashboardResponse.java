package com.ebank.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class DashboardResponse {
    
    private List<BankAccountResponse> comptes;
    private BankAccountResponse compteActuel;
    private Page<OperationResponse> operations;
    private String nomClient;
    private String prenomClient;
    
    // Default constructor
    public DashboardResponse() {}
    
    // All-args constructor
    public DashboardResponse(List<BankAccountResponse> comptes, BankAccountResponse compteActuel, Page<OperationResponse> operations, String nomClient, String prenomClient) {
        this.comptes = comptes;
        this.compteActuel = compteActuel;
        this.operations = operations;
        this.nomClient = nomClient;
        this.prenomClient = prenomClient;
    }
    
    // Builder pattern
    public static DashboardResponseBuilder builder() {
        return new DashboardResponseBuilder();
    }
    
    public static class DashboardResponseBuilder {
        private List<BankAccountResponse> comptes;
        private BankAccountResponse compteActuel;
        private Page<OperationResponse> operations;
        private String nomClient;
        private String prenomClient;
        
        public DashboardResponseBuilder comptes(List<BankAccountResponse> comptes) {
            this.comptes = comptes;
            return this;
        }
        
        public DashboardResponseBuilder compteActuel(BankAccountResponse compteActuel) {
            this.compteActuel = compteActuel;
            return this;
        }
        
        public DashboardResponseBuilder operations(Page<OperationResponse> operations) {
            this.operations = operations;
            return this;
        }
        
        public DashboardResponseBuilder nomClient(String nomClient) {
            this.nomClient = nomClient;
            return this;
        }
        
        public DashboardResponseBuilder prenomClient(String prenomClient) {
            this.prenomClient = prenomClient;
            return this;
        }
        
        public DashboardResponse build() {
            return new DashboardResponse(comptes, compteActuel, operations, nomClient, prenomClient);
        }
    }
    
    // Getter methods
    public List<BankAccountResponse> getComptes() {
        return comptes;
    }
    
    public BankAccountResponse getCompteActuel() {
        return compteActuel;
    }
    
    public Page<OperationResponse> getOperations() {
        return operations;
    }
    
    public String getNomClient() {
        return nomClient;
    }
    
    public String getPrenomClient() {
        return prenomClient;
    }
    
    // Setter methods
    public void setComptes(List<BankAccountResponse> comptes) {
        this.comptes = comptes;
    }
    
    public void setCompteActuel(BankAccountResponse compteActuel) {
        this.compteActuel = compteActuel;
    }
    
    public void setOperations(Page<OperationResponse> operations) {
        this.operations = operations;
    }
    
    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
    
    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }
} 