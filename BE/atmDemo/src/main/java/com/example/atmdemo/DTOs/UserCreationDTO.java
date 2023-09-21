package com.example.atmdemo.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
