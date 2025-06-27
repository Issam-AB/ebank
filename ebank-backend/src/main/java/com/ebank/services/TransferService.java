package com.ebank.services;

import com.ebank.dto.VirementRequest;
import com.ebank.entities.BankAccount;
import com.ebank.entities.Operation;
import com.ebank.enums.AccountStatus;
import com.ebank.enums.OperationType;
import com.ebank.repositories.BankAccountRepository;
import com.ebank.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class TransferService {
    
    private final BankAccountRepository bankAccountRepository;
    private final OperationRepository operationRepository;
    
    @Autowired
    public TransferService(BankAccountRepository bankAccountRepository, OperationRepository operationRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.operationRepository = operationRepository;
    }
    
    public void performTransfer(VirementRequest request) {
        // Clean RIBs for search (remove spaces and convert to uppercase)
        String cleanRibSource = request.getRibSource().replaceAll("\\s+", "").toUpperCase();
        String cleanRibDestination = request.getRibDestination().replaceAll("\\s+", "").toUpperCase();
        
        // Get source account
        BankAccount sourceAccount = bankAccountRepository.findActiveAccountByRib(cleanRibSource)
                .orElseThrow(() -> new IllegalArgumentException("Compte source non trouvé ou inactif"));
        
        // Get destination account
        BankAccount destinationAccount = bankAccountRepository.findActiveAccountByRib(cleanRibDestination)
                .orElseThrow(() -> new IllegalArgumentException("Compte destinataire non trouvé ou inactif"));
        
        // RG_11: Le compte bancaire ne doit pas être bloqué ou clôturé
        if (sourceAccount.getStatut() != AccountStatus.OUVERT) {
            throw new IllegalStateException("Le compte source est bloqué ou clôturé");
        }
        
        if (destinationAccount.getStatut() != AccountStatus.OUVERT) {
            throw new IllegalStateException("Le compte destinataire est bloqué ou clôturé");
        }
        
        // RG_12: Le solde de compte doit être supérieur au montant du virement
        if (sourceAccount.getSolde().compareTo(request.getMontant()) < 0) {
            throw new IllegalStateException("Solde insuffisant pour effectuer le virement");
        }
        
        // Perform the transfer
        BigDecimal sourceBalanceBefore = sourceAccount.getSolde();
        BigDecimal destinationBalanceBefore = destinationAccount.getSolde();
        
        // RG_13: Le compte du client sera débité du montant du virement
        sourceAccount.setSolde(sourceAccount.getSolde().subtract(request.getMontant()));
        sourceAccount.setLastActivityAt(LocalDateTime.now());
        
        // RG_14: Le compte du client destinataire sera crédité du montant du virement
        destinationAccount.setSolde(destinationAccount.getSolde().add(request.getMontant()));
        destinationAccount.setLastActivityAt(LocalDateTime.now());
        
        // Save updated accounts
        bankAccountRepository.save(sourceAccount);
        bankAccountRepository.save(destinationAccount);
        
        // RG_15: L'application doit tracer les deux opérations avec leurs dates précises
        LocalDateTime operationTime = LocalDateTime.now();
        
        // Create debit operation for source account
        Operation debitOperation = new Operation();
        debitOperation.setIntitule("Virement émis vers " + cleanRibDestination);
        debitOperation.setType(OperationType.DEBIT);
        debitOperation.setMontant(request.getMontant());
        debitOperation.setMotif(request.getMotif());
        debitOperation.setCompteSource(sourceAccount);
        debitOperation.setCompteDestination(destinationAccount);
        debitOperation.setDateOperation(operationTime);
        debitOperation.setSoldeAvant(sourceBalanceBefore);
        debitOperation.setSoldeApres(sourceAccount.getSolde());
        
        // Create credit operation for destination account
        Operation creditOperation = new Operation();
        creditOperation.setIntitule("Virement reçu de " + cleanRibSource);
        creditOperation.setType(OperationType.CREDIT);
        creditOperation.setMontant(request.getMontant());
        creditOperation.setMotif(request.getMotif());
        creditOperation.setCompteSource(sourceAccount);
        creditOperation.setCompteDestination(destinationAccount);
        creditOperation.setDateOperation(operationTime);
        creditOperation.setSoldeAvant(destinationBalanceBefore);
        creditOperation.setSoldeApres(destinationAccount.getSolde());
        
        // Save operations
        operationRepository.save(debitOperation);
        operationRepository.save(creditOperation);
    }
} 