package com.example.atmdemo.controllers;

import com.example.atmdemo.DTOs.*;
import com.example.atmdemo.models.User;
import com.example.atmdemo.security.JWT.JwtUtils;
import com.example.atmdemo.security.SecurityService;
import com.example.atmdemo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
@CrossOrigin(origins = {"http://localhost:4200"}, maxAge = 3600, allowCredentials = "true")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDTO registerUserDTO) {
        if (securityService.userExistsByEmail(registerUserDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        securityService.registerUser(registerUserDTO);
        return ResponseEntity.ok().body("Registration completed successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDTO loginUserDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ResponseCookie jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(authentication);
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(authentication);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                    .body(new LoginResponseDTO(userService.findByEmail(authentication.getName())
                            .orElseThrow(() -> new IllegalStateException("User not found"))
                            .getNickname()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        if (jwtUtils.getRefreshTokenValidateAndGenerateAccessToken(request) != null) {
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(jwtUtils.getRefreshTokenValidateAndGenerateAccessToken(request));
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build();
        }
        return ResponseEntity.badRequest().body("Refresh token is expired");
    }
    
    @PostMapping("password-change")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDTO passwordDTO, HttpServletRequest request) {
        User user = jwtUtils.getUserFromRequest(request);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        String newPassword = passwordDTO.getNewPassword();
        int minimumPasswordLength = 6;

        if (newPassword.length() < minimumPasswordLength) {
            String errorMessage = "Password must be at least " + minimumPasswordLength + " characters long";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        userService.changePassword(user, newPassword);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/person")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        User user = jwtUtils.getUserFromRequest(request);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        UserProfileDTO userDTO = new UserProfileDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/people")
    public ResponseEntity<?> getAllPeople() {
        List<User> people = userService.getAllUsers();
        return ResponseEntity.ok(people);
    }

    @PatchMapping("/person")
    public ResponseEntity<?> updateUserByNickname(HttpServletRequest request, @RequestBody UserUpdateDTO userUpdateDTO) {
        User requestUser = jwtUtils.getUserFromRequest(request);
        if (requestUser == null || userUpdateDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(requestUser, userUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/person")
    public ResponseEntity<?> deletePerson(HttpServletRequest request) {
        User user = jwtUtils.getUserFromRequest(request);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
    }
}
