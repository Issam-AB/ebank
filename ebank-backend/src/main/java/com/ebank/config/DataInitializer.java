package com.ebank.config;

import com.ebank.entities.User;
import com.ebank.enums.UserRole;
import com.ebank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Check if users already exist
        if (userRepository.count() == 0) {
            initializeTestUsers();
        }
    }
    
    private void initializeTestUsers() {
        // Create test users
        User agent = new User();
        agent.setNom("Agent");
        agent.setPrenom("Guichet");
        agent.setNumeroIdentite("AGENT001");
        agent.setDateNaissance(LocalDate.of(1985, 1, 1));
        agent.setEmail("agent@ebank.com");
        agent.setAdressePostale("123 Rue de la Banque, Paris");
        agent.setLogin("agent1");
        agent.setPassword(passwordEncoder.encode("password"));
        agent.setRole(UserRole.AGENT_GUICHET);
        agent.setEnabled(true);
        agent.setAccountNonExpired(true);
        agent.setAccountNonLocked(true);
        agent.setCredentialsNonExpired(true);
        agent.setCreatedAt(LocalDateTime.now());
        agent.setUpdatedAt(LocalDateTime.now());
        
        User client1 = new User();
        client1.setNom("Dupont");
        client1.setPrenom("Jean");
        client1.setNumeroIdentite("CLIENT001");
        client1.setDateNaissance(LocalDate.of(1990, 5, 15));
        client1.setEmail("jean.dupont@email.com");
        client1.setAdressePostale("456 Avenue des Clients, Lyon");
        client1.setLogin("client1");
        client1.setPassword(passwordEncoder.encode("password"));
        client1.setRole(UserRole.CLIENT);
        client1.setEnabled(true);
        client1.setAccountNonExpired(true);
        client1.setAccountNonLocked(true);
        client1.setCredentialsNonExpired(true);
        client1.setCreatedAt(LocalDateTime.now());
        client1.setUpdatedAt(LocalDateTime.now());
        
        User client2 = new User();
        client2.setNom("Martin");
        client2.setPrenom("Marie");
        client2.setNumeroIdentite("CLIENT002");
        client2.setDateNaissance(LocalDate.of(1988, 8, 20));
        client2.setEmail("marie.martin@email.com");
        client2.setAdressePostale("789 Boulevard des Comptes, Marseille");
        client2.setLogin("client2");
        client2.setPassword(passwordEncoder.encode("password"));
        client2.setRole(UserRole.CLIENT);
        client2.setEnabled(true);
        client2.setAccountNonExpired(true);
        client2.setAccountNonLocked(true);
        client2.setCredentialsNonExpired(true);
        client2.setCreatedAt(LocalDateTime.now());
        client2.setUpdatedAt(LocalDateTime.now());
        
        // Save users
        userRepository.save(agent);
        userRepository.save(client1);
        userRepository.save(client2);
        
        System.out.println("Test users initialized successfully!");
    }
} 