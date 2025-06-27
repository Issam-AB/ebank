package com.ebank.controllers;

import com.ebank.dto.ApiResponse;
import com.ebank.dto.DashboardResponse;
import com.ebank.dto.OperationResponse;
import com.ebank.entities.User;
import com.ebank.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@PreAuthorize("hasRole('CLIENT')")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<DashboardResponse>> getDashboard(
            @RequestParam(required = false) String rib,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();
            
            DashboardResponse dashboard = dashboardService.getClientDashboard(user.getId(), rib, page, size);
            return ResponseEntity.ok(ApiResponse.success(dashboard, "Tableau de bord récupéré avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération du tableau de bord", e.getMessage()));
        }
    }
    
    @GetMapping("/operations/{rib}")
    public ResponseEntity<ApiResponse<Page<OperationResponse>>> getAccountOperations(
            @PathVariable String rib,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<OperationResponse> operations = dashboardService.getAccountOperations(rib, page, size);
            return ResponseEntity.ok(ApiResponse.success(operations, "Opérations récupérées avec succès"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur lors de la récupération des opérations", e.getMessage()));
        }
    }
} 