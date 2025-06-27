package com.ebank.controllers;

import com.ebank.dto.ApiResponse;
import com.ebank.dto.BankAccountRequest;
import com.ebank.dto.BankAccountResponse;
import com.ebank.services.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@PreAuthorize("hasRole('AGENT_GUICHET')")
public class BankAccountController {
    
    private final BankAccountService bankAccountService;
    
    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('AGENT_GUICHET')")
    public ResponseEntity<ApiResponse<BankAccountResponse>> createBankAccount(@Valid @RequestBody BankAccountRequest request) {
        try {
            BankAccountResponse response = bankAccountService.createBankAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(response, "Compte bancaire créé avec succès"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur de validation", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la création du compte", e.getMessage()));
        }
    }
    
    @GetMapping("/{rib}")
    @PreAuthorize("hasRole('CLIENT') or hasRole('AGENT_GUICHET')")
    public ResponseEntity<ApiResponse<BankAccountResponse>> getAccountByRib(@PathVariable String rib) {
        try {
            BankAccountResponse response = bankAccountService.getAccountByRib(rib);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur lors de la récupération du compte", e.getMessage()));
        }
    }
} 