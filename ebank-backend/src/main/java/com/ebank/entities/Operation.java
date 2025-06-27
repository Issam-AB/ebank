package com.ebank.entities;

import com.ebank.enums.OperationType;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@EntityListeners(AuditingEntityListener.class)
public class Operation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String intitule;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType type;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;
    
    @Column
    private String motif;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_source_id", nullable = false)
    private BankAccount compteSource;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_destination_id")
    private BankAccount compteDestination;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime dateOperation;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal soldeAvant;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal soldeApres;
    
    // Explicit setter methods for Lombok compatibility
    public void setType(OperationType type) {
        this.type = type;
    }
    
    public void setCompteSource(BankAccount compteSource) {
        this.compteSource = compteSource;
    }
    
    public void setCompteDestination(BankAccount compteDestination) {
        this.compteDestination = compteDestination;
    }
    
    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }
    
    public void setSoldeAvant(BigDecimal soldeAvant) {
        this.soldeAvant = soldeAvant;
    }
    
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
    public void setMotif(String motif) {
        this.motif = motif;
    }
    
    public void setSoldeApres(BigDecimal soldeApres) {
        this.soldeApres = soldeApres;
    }
    
    // Additional getter and setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIntitule() {
        return intitule;
    }
    
    public OperationType getType() {
        return type;
    }
    
    public BigDecimal getMontant() {
        return montant;
    }
    
    public String getMotif() {
        return motif;
    }
    
    public BankAccount getCompteSource() {
        return compteSource;
    }
    
    public BankAccount getCompteDestination() {
        return compteDestination;
    }
    
    public LocalDateTime getDateOperation() {
        return dateOperation;
    }
    
    public BigDecimal getSoldeAvant() {
        return soldeAvant;
    }
    
    public BigDecimal getSoldeApres() {
        return soldeApres;
    }
} 