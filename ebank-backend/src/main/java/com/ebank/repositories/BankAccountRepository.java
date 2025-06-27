package com.ebank.repositories;

import com.ebank.entities.BankAccount;
import com.ebank.entities.User;
import com.ebank.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    
    Optional<BankAccount> findByRib(String rib);
    
    boolean existsByRib(String rib);
    
    List<BankAccount> findByClient(User client);
    
    List<BankAccount> findByClientId(Long clientId);
    
    List<BankAccount> findByStatut(AccountStatus statut);
    
    @Query("SELECT ba FROM BankAccount ba WHERE ba.client.id = :clientId AND ba.statut = :statut")
    List<BankAccount> findByClientIdAndStatut(@Param("clientId") Long clientId, @Param("statut") AccountStatus statut);
    
    @Query("SELECT ba FROM BankAccount ba WHERE ba.client.id = :clientId ORDER BY ba.lastActivityAt DESC NULLS LAST, ba.createdAt DESC")
    List<BankAccount> findByClientIdOrderByLastActivity(@Param("clientId") Long clientId);
    
    @Query("SELECT ba FROM BankAccount ba WHERE ba.rib = :rib AND ba.statut = 'OUVERT'")
    Optional<BankAccount> findActiveAccountByRib(@Param("rib") String rib);
    
    @Query("SELECT COUNT(ba) FROM BankAccount ba WHERE ba.client.id = :clientId")
    long countByClientId(@Param("clientId") Long clientId);
} 