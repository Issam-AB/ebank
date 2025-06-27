package com.ebank.controllers;

import com.ebank.dto.ApiResponse;
import com.ebank.dto.VirementRequest;
import com.ebank.services.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@PreAuthorize("hasRole('CLIENT')")
public class TransferController {
    
    private final TransferService transferService;
    
    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<String>> performTransfer(@Valid @RequestBody VirementRequest request) {
        try {
            transferService.performTransfer(request);
            return ResponseEntity.ok(ApiResponse.success("", "Virement effectué avec succès"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur de validation", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur de transaction", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Erreur lors du virement", e.getMessage()));
        }
    }
} 