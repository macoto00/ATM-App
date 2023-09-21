package com.example.atmdemo.security.JWT;

import com.example.atmdemo.DTOs.UserCreationDTO;
import com.example.atmdemo.models.User;
import com.example.atmdemo.repositories.UserRepository;
import com.example.atmdemo.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void registerUser(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setFirstName(userCreationDTO.getFirstName());
        user.setLastName(userCreationDTO.getLastName());
        user.setEmail(userCreationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreationDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String generateToken() {
        UUID tokenUUID = UUID.randomUUID();
        return tokenUUID.toString();
    }

    @Override
    public String generateNickname(String firstName, String lastName) {
        firstName = cleanseInput(firstName);
        lastName = cleanseInput(lastName);

        if (userRepository.existsByNickname(firstName + "-" + lastName)) {
            int index = 1;
            while (userRepository.existsByNickname(firstName + "-" + lastName + index)) {
                index++;
            }
            return (firstName + "-" + lastName + index).toLowerCase();
        }
        return (firstName + "-" + lastName).toLowerCase();
    }

    @Override
    public String cleanseInput(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}
