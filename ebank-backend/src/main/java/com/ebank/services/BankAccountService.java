package com.ebank.services;

import com.ebank.dto.BankAccountRequest;
import com.ebank.dto.BankAccountResponse;
import com.ebank.entities.BankAccount;
import com.ebank.entities.User;
import com.ebank.enums.AccountStatus;
import com.ebank.repositories.BankAccountRepository;
import com.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountService {
    
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }
    
    public BankAccountResponse createBankAccount(BankAccountRequest request) {
        // RG_8: Le numéro d'identité doit exister au niveau de la base de données
        User client = userRepository.findByNumeroIdentite(request.getNumeroIdentiteClient())
                .orElseThrow(() -> new IllegalArgumentException("Client avec ce numéro d'identité non trouvé"));
        
        // RG_9: Le RIB doit être un RIB valide (basic validation)
        if (!isValidRib(request.getRib())) {
            throw new IllegalArgumentException("RIB invalide");
        }
        
        // Clean RIB (remove spaces and convert to uppercase)
        String cleanRib = request.getRib().replaceAll("\\s+", "").toUpperCase();
        
        // Check if RIB already exists
        if (bankAccountRepository.existsByRib(cleanRib)) {
            throw new IllegalArgumentException("Ce RIB existe déjà");
        }
        
        // Create bank account
        BankAccount bankAccount = new BankAccount();
        bankAccount.setRib(cleanRib); // Store cleaned RIB
        bankAccount.setClient(client);
        bankAccount.setStatut(AccountStatus.OUVERT); // RG_10: Statut "Ouvert"
        bankAccount.setLastActivityAt(LocalDateTime.now());
        
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        
        return BankAccountResponse.builder()
                .id(savedAccount.getId())
                .rib(savedAccount.getRib())
                .solde(savedAccount.getSolde())
                .statut(savedAccount.getStatut())
                .clientNom(client.getNom())
                .clientPrenom(client.getPrenom())
                .lastActivityAt(savedAccount.getLastActivityAt())
                .createdAt(savedAccount.getCreatedAt())
                .build();
    }
    
    public List<BankAccountResponse> getAccountsByClientId(Long clientId) {
        List<BankAccount> accounts = bankAccountRepository.findByClientIdOrderByLastActivity(clientId);
        return accounts.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public BankAccountResponse getAccountByRib(String rib) {
        // Clean RIB for search (remove spaces and convert to uppercase)
        String cleanRib = rib.replaceAll("\\s+", "").toUpperCase();
        
        BankAccount account = bankAccountRepository.findByRib(cleanRib)
                .orElseThrow(() -> new IllegalArgumentException("Compte non trouvé"));
        return mapToResponse(account);
    }
    
    private BankAccountResponse mapToResponse(BankAccount account) {
        return BankAccountResponse.builder()
                .id(account.getId())
                .rib(account.getRib())
                .solde(account.getSolde())
                .statut(account.getStatut())
                .clientNom(account.getClient().getNom())
                .clientPrenom(account.getClient().getPrenom())
                .lastActivityAt(account.getLastActivityAt())
                .createdAt(account.getCreatedAt())
                .build();
    }
    
    private boolean isValidRib(String rib) {
        // Basic RIB validation for French format
        if (rib == null || rib.trim().isEmpty()) {
            return false;
        }
        
        // Remove spaces and convert to uppercase for validation
        String cleanRib = rib.replaceAll("\\s+", "").toUpperCase();
        
        // French RIB format: 2 letters (country) + 2 digits + 4 alphanumeric + 7 digits + optional 16 alphanumeric
        // Example: FR1420041010050500013M02606
        return cleanRib.length() >= 20 && 
               cleanRib.length() <= 34 && 
               cleanRib.matches("^[A-Z]{2}[0-9]{2}[A-Z0-9]{4}[0-9]{7}([A-Z0-9]?){0,16}$");
    }
} 