package com.example.atmdemo.services;

import com.example.atmdemo.DTOs.UserCreationDTO;
import com.example.atmdemo.DTOs.UserUpdateDTO;
import com.example.atmdemo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(UserCreationDTO userCreationDTO);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    void updateUser(UserUpdateDTO userUpdateDTO, Long id);
    void deleteUser(Long id);
    boolean userExistsByEmail(String email);
}
