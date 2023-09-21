package com.example.atmdemo.security;

import com.example.atmdemo.DTOs.UserCreationDTO;

public interface SecurityService {
    boolean userExistsByEmail(String email);
    void registerUser(UserCreationDTO userCreationDTO);
    String generateToken();
    String generateNickname(String firstName, String lastName);
    String cleanseInput(String input);
}
