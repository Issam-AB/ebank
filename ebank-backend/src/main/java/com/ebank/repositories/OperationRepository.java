package com.ebank.repositories;

import com.ebank.entities.BankAccount;
import com.ebank.entities.Operation;
import com.ebank.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    
    @Query("SELECT o FROM Operation o WHERE o.compteSource.id = :accountId OR o.compteDestination.id = :accountId ORDER BY o.dateOperation DESC")
    Page<Operation> findByAccountId(@Param("accountId") Long accountId, Pageable pageable);
    
    @Query("SELECT o FROM Operation o WHERE o.compteSource.rib = :rib OR o.compteDestination.rib = :rib ORDER BY o.dateOperation DESC")
    Page<Operation> findByRib(@Param("rib") String rib, Pageable pageable);
    
    @Query("SELECT o FROM Operation o WHERE o.compteSource.rib = :rib OR o.compteDestination.rib = :rib ORDER BY o.dateOperation DESC")
    List<Operation> findLastOperationsByRib(@Param("rib") String rib, Pageable pageable);
    
    List<Operation> findByCompteSourceOrderByDateOperationDesc(BankAccount compteSource);
    
    List<Operation> findByCompteDestinationOrderByDateOperationDesc(BankAccount compteDestination);
    
    @Query("SELECT o FROM Operation o WHERE o.type = :type AND o.dateOperation BETWEEN :startDate AND :endDate")
    List<Operation> findByTypeAndDateRange(@Param("type") OperationType type, 
                                          @Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(o) FROM Operation o WHERE o.compteSource.id = :accountId OR o.compteDestination.id = :accountId")
    long countOperationsByAccountId(@Param("accountId") Long accountId);
} 