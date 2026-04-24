package com.tutorial.inventory.orders;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, UUID> { }
