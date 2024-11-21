package com.example.demo.controller;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Le nom d'utilisateur existe déjà !");
        }
        User user = new User(registerRequest.getFullName(), registerRequest.getUsername(),
                passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getRole());
        userRepository.save(user);
        return "Utilisateur enregistré avec succès !";
    }

    @PostMapping("/login")
    public AuthResponseDTO authenticate(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            String token = jwtUtil.generateToken(authRequest.getUsername());
            return new AuthResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Identifiants invalides");
        }
    }
}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    // Getters et setters
}

class RegisterRequest {
    private String fullName;
    private String role;
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getRole() {
        return this.role;
    }

    public String getPassword() {
        return this.password;
    }
    // Getters et setters
}
