package com.ebank.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    
    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void sendNewClientCredentials(String toEmail, String clientName, String login, String temporaryPassword) {
        try {
            // For development: Log the email instead of sending it
            String emailBody = buildNewClientEmailBody(clientName, login, temporaryPassword);
            
            log.info("=== EMAIL WOULD BE SENT ===");
            log.info("To: {}", toEmail);
            log.info("Subject: Bienvenue chez eBank - Vos identifiants de connexion");
            log.info("Body:\n{}", emailBody);
            log.info("=== END EMAIL ===");
            
            // Uncomment the following lines to actually send emails when you have proper email config
            /*
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Bienvenue chez eBank - Vos identifiants de connexion");
            message.setText(emailBody);
            
            mailSender.send(message);
            log.info("Email envoyé avec succès à: {}", toEmail);
            */
            
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email à {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Erreur lors de l'envoi de l'email: " + e.getMessage());
        }
    }
    
    private String buildNewClientEmailBody(String clientName, String login, String temporaryPassword) {
        return String.format("""
                Bonjour %s,
                
                Bienvenue chez eBank !
                
                Votre compte client a été créé avec succès. Voici vos identifiants de connexion :
                
                Login : %s
                Mot de passe temporaire : %s
                
                Pour votre sécurité, nous vous recommandons fortement de changer votre mot de passe lors de votre première connexion.
                
                Vous pouvez accéder à votre espace client à l'adresse : http://localhost:3000
                
                En cas de problème, n'hésitez pas à contacter votre conseiller.
                
                Cordialement,
                L'équipe eBank
                """, clientName, login, temporaryPassword);
    }
} 