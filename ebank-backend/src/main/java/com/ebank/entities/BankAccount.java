package com.ebank.entities;

import com.ebank.enums.AccountStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@EntityListeners(AuditingEntityListener.class)
public class BankAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false, length = 34)
    private String rib;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus statut = AccountStatus.OUVERT;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;
    
    @OneToMany(mappedBy = "compteSource", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operationsSource;
    
    @OneToMany(mappedBy = "compteDestination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Operation> operationsDestination;
    
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @Column
    private LocalDateTime lastActivityAt;
    
    // Explicit getter and setter methods for Lombok compatibility
    public BigDecimal getSolde() {
        return solde;
    }
    
    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }
    
    public AccountStatus getStatut() {
        return statut;
    }
    
    public void setStatut(AccountStatus statut) {
        this.statut = statut;
    }
    
    public LocalDateTime getLastActivityAt() {
        return lastActivityAt;
    }
    
    public void setLastActivityAt(LocalDateTime lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }
    
    // Additional getter and setter methods
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRib() {
        return rib;
    }
    
    public void setRib(String rib) {
        this.rib = rib;
    }
    
    public User getClient() {
        return client;
    }
    
    public void setClient(User client) {
        this.client = client;
    }
    
    public List<Operation> getOperationsSource() {
        return operationsSource;
    }
    
    public void setOperationsSource(List<Operation> operationsSource) {
        this.operationsSource = operationsSource;
    }
    
    public List<Operation> getOperationsDestination() {
        return operationsDestination;
    }
    
    public void setOperationsDestination(List<Operation> operationsDestination) {
        this.operationsDestination = operationsDestination;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 