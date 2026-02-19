package com.car.management.car_management.controller;

import com.car.management.car_management.dto.request.UserLoginDTO;
import com.car.management.car_management.dto.request.UserRequestDTO;
import com.car.management.car_management.entity.User;
import com.car.management.car_management.jwt.JwtToken;
import com.car.management.car_management.repository.UserRepository;
import com.car.management.car_management.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000") // Permite acesso do Next.js
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository; // Para salvar no banco

    @Autowired
    private PasswordEncoder passwordEncoder; // Para criptografar a senha

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody @Valid UserLoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        String token = jwtUtils.generateToken(authentication.getName());
        return ResponseEntity.ok(new JwtToken(token));
    }

    @PostMapping("/register") // Resolve o erro "No static resource"
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO requestDTO) {
        // 1. Verifica se o e-mail já existe para evitar erro 500
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email already registered!");
        }

        // 2. Transforma o DTO em Entity e encripta a senha
        User newUser = User.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role(User.Role.ROLE_CLIENT)
                .build();

        // 3. Salva efetivamente no banco de dados
        userRepository.save(newUser);

        // Retorna 200 OK para o frontend mostrar o pop-up de 5 segundos
        return ResponseEntity.ok().body("Account created successfully!");
    }
}