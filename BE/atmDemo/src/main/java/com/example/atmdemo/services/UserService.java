package com.example.atmdemo.services;

import com.example.atmdemo.DTOs.RegisterUserDTO;
import com.example.atmdemo.DTOs.UserUpdateDTO;
import com.example.atmdemo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(RegisterUserDTO registerUserDTO);
    Optional<User> getUserById(Long id);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    void updateUser(User user, UserUpdateDTO userUpdateDTO);
    void deleteUser(User user);
    boolean userExistsByEmail(String email);
    void changePassword(User user, String newPassword);
}
