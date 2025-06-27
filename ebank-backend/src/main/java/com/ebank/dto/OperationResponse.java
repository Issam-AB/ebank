package com.ebank.dto;

import com.ebank.enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperationResponse {
    
    private Long id;
    private String intitule;
    private OperationType type;
    private BigDecimal montant;
    private String motif;
    private LocalDateTime dateOperation;
    private String ribSource;
    private String ribDestination;
    private BigDecimal soldeAvant;
    private BigDecimal soldeApres;
    
    // Default constructor
    public OperationResponse() {}
    
    // All-args constructor
    public OperationResponse(Long id, String intitule, OperationType type, BigDecimal montant, String motif, LocalDateTime dateOperation, String ribSource, String ribDestination, BigDecimal soldeAvant, BigDecimal soldeApres) {
        this.id = id;
        this.intitule = intitule;
        this.type = type;
        this.montant = montant;
        this.motif = motif;
        this.dateOperation = dateOperation;
        this.ribSource = ribSource;
        this.ribDestination = ribDestination;
        this.soldeAvant = soldeAvant;
        this.soldeApres = soldeApres;
    }
    
    // Builder pattern
    public static OperationResponseBuilder builder() {
        return new OperationResponseBuilder();
    }
    
    public static class OperationResponseBuilder {
        private Long id;
        private String intitule;
        private OperationType type;
        private BigDecimal montant;
        private String motif;
        private LocalDateTime dateOperation;
        private String ribSource;
        private String ribDestination;
        private BigDecimal soldeAvant;
        private BigDecimal soldeApres;
        
        public OperationResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }
        
        public OperationResponseBuilder intitule(String intitule) {
            this.intitule = intitule;
            return this;
        }
        
        public OperationResponseBuilder type(OperationType type) {
            this.type = type;
            return this;
        }
        
        public OperationResponseBuilder montant(BigDecimal montant) {
            this.montant = montant;
            return this;
        }
        
        public OperationResponseBuilder motif(String motif) {
            this.motif = motif;
            return this;
        }
        
        public OperationResponseBuilder dateOperation(LocalDateTime dateOperation) {
            this.dateOperation = dateOperation;
            return this;
        }
        
        public OperationResponseBuilder ribSource(String ribSource) {
            this.ribSource = ribSource;
            return this;
        }
        
        public OperationResponseBuilder ribDestination(String ribDestination) {
            this.ribDestination = ribDestination;
            return this;
        }
        
        public OperationResponseBuilder soldeAvant(BigDecimal soldeAvant) {
            this.soldeAvant = soldeAvant;
            return this;
        }
        
        public OperationResponseBuilder soldeApres(BigDecimal soldeApres) {
            this.soldeApres = soldeApres;
            return this;
        }
        
        public OperationResponse build() {
            return new OperationResponse(id, intitule, type, montant, motif, dateOperation, ribSource, ribDestination, soldeAvant, soldeApres);
        }
    }
    
    // Getter methods
    public Long getId() {
        return id;
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
    
    public LocalDateTime getDateOperation() {
        return dateOperation;
    }
    
    public String getRibSource() {
        return ribSource;
    }
    
    public String getRibDestination() {
        return ribDestination;
    }
    
    public BigDecimal getSoldeAvant() {
        return soldeAvant;
    }
    
    public BigDecimal getSoldeApres() {
        return soldeApres;
    }
    
    // Setter methods
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    
    public void setType(OperationType type) {
        this.type = type;
    }
    
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    
    public void setMotif(String motif) {
        this.motif = motif;
    }
    
    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }
    
    public void setRibSource(String ribSource) {
        this.ribSource = ribSource;
    }
    
    public void setRibDestination(String ribDestination) {
        this.ribDestination = ribDestination;
    }
    
    public void setSoldeAvant(BigDecimal soldeAvant) {
        this.soldeAvant = soldeAvant;
    }
    
    public void setSoldeApres(BigDecimal soldeApres) {
        this.soldeApres = soldeApres;
    }
}