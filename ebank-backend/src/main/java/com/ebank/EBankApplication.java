package com.ebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableJpaAuditing
public class EBankApplication {
    static {
        // Load .env file if present
        Dotenv.configure().ignoreIfMissing().load();
    }

    public static void main(String[] args) {
        SpringApplication.run(EBankApplication.class, args);
    }
} 