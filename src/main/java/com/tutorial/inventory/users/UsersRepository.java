package com.tutorial.inventory.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, UUID> { }
