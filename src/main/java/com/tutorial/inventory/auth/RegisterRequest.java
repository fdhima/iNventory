package com.tutorial.inventory.auth;

public record RegisterRequest(String username, String name, String email, String password) {}
