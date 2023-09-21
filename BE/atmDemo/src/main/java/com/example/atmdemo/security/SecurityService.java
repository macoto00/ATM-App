package com.example.atmdemo.security;

import com.example.atmdemo.DTOs.RegisterUserDTO;

public interface SecurityService {
    boolean userExistsByEmail(String email);
    void registerUser(RegisterUserDTO registerUserDTO);
    String generateToken();
    String generateNickname(String firstName, String lastName);
    String cleanseInput(String input);
}
