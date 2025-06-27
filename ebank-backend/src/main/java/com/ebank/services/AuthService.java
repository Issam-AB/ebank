package com.ebank.services;

import com.ebank.dto.AuthResponse;
import com.ebank.dto.ChangePasswordRequest;
import com.ebank.dto.LoginRequest;
import com.ebank.entities.User;
import com.ebank.repositories.UserRepository;
import com.ebank.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getLogin(),
                            loginRequest.getPassword()
                    )
            );
            
            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.generateToken(authentication);
            
            return AuthResponse.builder()
                    .token(token)
                    .tokenType("Bearer")
                    .userId(user.getId())
                    .login(user.getLogin())
                    .nom(user.getNom())
                    .prenom(user.getPrenom())
                    .role(user.getRole())
                    .expiresIn(jwtUtil.getExpirationTime())
                    .build();
                    
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Login ou mot de passe erronés");
        }
    }
    
    public void changePassword(String login, ChangePasswordRequest request) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        
        // Verify old password
        if (!passwordEncoder.matches(request.getAncienMotDePasse(), user.getPassword())) {
            throw new BadCredentialsException("L'ancien mot de passe est incorrect");
        }
        
        // Verify password confirmation
        if (!request.getNouveauMotDePasse().equals(request.getConfirmationMotDePasse())) {
            throw new IllegalArgumentException("La confirmation du mot de passe ne correspond pas");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(request.getNouveauMotDePasse()));
        userRepository.save(user);
    }
} 