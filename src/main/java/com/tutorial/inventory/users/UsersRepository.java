package com.tutorial.inventory.users;

import java.util.UUID;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByUsername(String username);
}
