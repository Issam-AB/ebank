package com.ebank.services;

import com.ebank.dto.BankAccountResponse;
import com.ebank.dto.DashboardResponse;
import com.ebank.dto.OperationResponse;
import com.ebank.entities.BankAccount;
import com.ebank.entities.Operation;
import com.ebank.entities.User;
import com.ebank.repositories.BankAccountRepository;
import com.ebank.repositories.OperationRepository;
import com.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardService {
    
    private final BankAccountRepository bankAccountRepository;
    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public DashboardService(BankAccountRepository bankAccountRepository, OperationRepository operationRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
    }
    
    public DashboardResponse getClientDashboard(Long clientId, String selectedRib, int page, int size) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client non trouvé"));
        
        // DEBUG: Fetch all accounts and print info
        List<BankAccount> accounts = bankAccountRepository.findAll();
        System.out.println("All accounts: " + accounts.size());
        for (BankAccount acc : accounts) {
            System.out.println("Account RIB: " + acc.getRib() + ", clientId: " + (acc.getClient() != null ? acc.getClient().getId() : null));
        }
        // Original code (commented out for debug)
        // List<BankAccount> accounts = bankAccountRepository.findByClientIdOrderByLastActivity(clientId);
        List<BankAccountResponse> accountResponses = accounts.stream()
                .map(this::mapToAccountResponse)
                .collect(Collectors.toList());
        
        // Determine current account (selected or most recent)
        BankAccount currentAccount;
        if (selectedRib != null) {
            currentAccount = accounts.stream()
                    .filter(acc -> acc.getRib().equals(selectedRib))
                    .findFirst()
                    .orElse(accounts.isEmpty() ? null : accounts.get(0));
        } else {
            currentAccount = accounts.isEmpty() ? null : accounts.get(0);
        }
        
        BankAccountResponse currentAccountResponse = null;
        Page<OperationResponse> operations = null;
        
        if (currentAccount != null) {
            currentAccountResponse = mapToAccountResponse(currentAccount);
            
            // Get operations for the current account with pagination
            Pageable pageable = PageRequest.of(page, size);
            Page<Operation> operationPage = operationRepository.findByAccountId(currentAccount.getId(), pageable);
            operations = operationPage.map(this::mapToOperationResponse);
        }
        
        return DashboardResponse.builder()
                .comptes(accountResponses)
                .compteActuel(currentAccountResponse)
                .operations(operations)
                .nomClient(client.getNom())
                .prenomClient(client.getPrenom())
                .build();
    }
    
    public Page<OperationResponse> getAccountOperations(String rib, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Operation> operations = operationRepository.findByRib(rib, pageable);
        return operations.map(this::mapToOperationResponse);
    }
    
    private BankAccountResponse mapToAccountResponse(BankAccount account) {
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
    
    private OperationResponse mapToOperationResponse(Operation operation) {
        return OperationResponse.builder()
                .id(operation.getId())
                .intitule(operation.getIntitule())
                .type(operation.getType())
                .montant(operation.getMontant())
                .motif(operation.getMotif())
                .dateOperation(operation.getDateOperation())
                .ribSource(operation.getCompteSource().getRib())
                .ribDestination(operation.getCompteDestination() != null ? 
                        operation.getCompteDestination().getRib() : null)
                .soldeAvant(operation.getSoldeAvant())
                .soldeApres(operation.getSoldeApres())
                .build();
    }
} 