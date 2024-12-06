package com.rtd.QuizeBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rtd.QuizeBackend.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    // You can define custom queries here if needed
    
    // Find a user by username
    // Optional<User> findByUsername(String username);

    // Find a user by email
    Optional<User> findByUserEmail(String email);

    // // Check if a user exists by username
    // boolean existsByUsername(String username);

    // // Check if a user exists by email
    // boolean existsByEmail(String email);
}
