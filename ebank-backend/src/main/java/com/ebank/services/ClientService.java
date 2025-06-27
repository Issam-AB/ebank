package com.ebank.services;

import com.ebank.dto.ClientRequest;
import com.ebank.dto.ClientResponse;
import com.ebank.entities.User;
import com.ebank.enums.UserRole;
import com.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@Transactional
public class ClientService {
    
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public ClientService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    
    public ClientResponse createClient(ClientRequest request) {
        // RG_4: Le numéro d'identité doit être unique
        if (userRepository.existsByNumeroIdentite(request.getNumeroIdentite())) {
            throw new IllegalArgumentException("Ce numéro d'identité existe déjà");
        }
        
        // RG_6: L'adresse mail doit être unique
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Cette adresse email est déjà utilisée");
        }
        
        // Generate unique login
        String login = generateUniqueLogin(request.getNom(), request.getPrenom());
        
        // Generate temporary password
        String temporaryPassword = generateTemporaryPassword();
        
        // Create user entity
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setNumeroIdentite(request.getNumeroIdentite());
        user.setDateNaissance(request.getDateNaissance());
        user.setEmail(request.getEmail());
        user.setAdressePostale(request.getAdressePostale());
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(temporaryPassword)); // RG_1: Password encryption
        user.setRole(UserRole.CLIENT);
        
        User savedUser = userRepository.save(user);
        
        // RG_7: Send email with credentials (optional - don't fail if email fails)
        String emailMessage = "";
        try {
            String clientFullName = request.getPrenom() + " " + request.getNom();
            emailService.sendNewClientCredentials(request.getEmail(), clientFullName, login, temporaryPassword);
            emailMessage = " Un email avec les identifiants a été envoyé.";
        } catch (Exception e) {
            // Log error but don't fail the transaction
            emailMessage = " Note: L'envoi de l'email a échoué, mais le client a été créé.";
        }
        
        return ClientResponse.builder()
                .id(savedUser.getId())
                .nom(savedUser.getNom())
                .prenom(savedUser.getPrenom())
                .numeroIdentite(savedUser.getNumeroIdentite())
                .dateNaissance(savedUser.getDateNaissance())
                .email(savedUser.getEmail())
                .adressePostale(savedUser.getAdressePostale())
                .login(savedUser.getLogin())
                .createdAt(savedUser.getCreatedAt())
                .message("Client créé avec succès." + emailMessage)
                .build();
    }
    
    private String generateUniqueLogin(String nom, String prenom) {
        String baseLogin = (prenom.substring(0, 1) + nom).toLowerCase().replaceAll("[^a-z0-9]", "");
        String login = baseLogin;
        int counter = 1;
        
        while (userRepository.existsByLogin(login)) {
            login = baseLogin + counter;
            counter++;
        }
        
        return login;
    }
    
    private String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
} 