package com.example.atmdemo.controllers;

import com.example.atmdemo.DTOs.UserCreationDTO;
import com.example.atmdemo.DTOs.UserUpdateDTO;
import com.example.atmdemo.models.User;
import com.example.atmdemo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Throwable e) {
            return ResponseEntity.status(404).body("User not found for ID: " + id);
        }
    }

    @GetMapping("/people")
    public ResponseEntity<?> getAllPeople() {
        List<User> people = userService.getAllUsers();
        return ResponseEntity.ok(people);
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserCreationDTO userCreationDTO) {
        try {
            if (userService.userExistsByEmail(userCreationDTO.getEmail())) {
                return ResponseEntity.status(400).body("User with this email already exists.");
            }
            userService.createUser(userCreationDTO);
            return ResponseEntity.ok("User created successfully");
        } catch (Throwable e){
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            userService.updateUser(userUpdateDTO, id);
            return ResponseEntity.ok("User updated successfully");
        } catch (Throwable e) {
            return ResponseEntity.status(500).body("Error updating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Throwable e) {
            return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
        }
    }
}
