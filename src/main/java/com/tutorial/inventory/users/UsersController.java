package com.tutorial.inventory.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersRepository usersRepo;

    public UsersController(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public Page<Users> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return usersRepo.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable UUID id) {
        return usersRepo.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users createUser(@RequestBody Users user) {
        return usersRepo.save(user);
    }

    @PutMapping("/{id}")
    public Users updateUser(@PathVariable UUID id, @RequestBody Users updatedUser) {
        return usersRepo.findById(id)
        .map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPasswordHash(updatedUser.getPasswordHash());
            user.setRole(updatedUser.getRole());
            user.setLastLoginAt(updatedUser.getLastLoginAt());
            return usersRepo.save(user);
        })
        .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        usersRepo.deleteById(id);
    }
}
