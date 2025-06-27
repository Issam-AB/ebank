package com.ebank.repositories;

import com.ebank.entities.User;
import com.ebank.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByLogin(String login);
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByNumeroIdentite(String numeroIdentite);
    
    boolean existsByLogin(String login);
    
    boolean existsByEmail(String email);
    
    boolean existsByNumeroIdentite(String numeroIdentite);
    
    List<User> findByRole(UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.enabled = true")
    List<User> findActiveUsersByRole(@Param("role") UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.login = :login AND u.enabled = true")
    Optional<User> findActiveUserByLogin(@Param("login") String login);
} 