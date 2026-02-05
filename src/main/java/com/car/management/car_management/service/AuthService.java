package com.car.management.car_management.service;

import com.car.management.car_management.dto.request.UserLoginDTO;
import com.car.management.car_management.entity.User;
import com.car.management.car_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String authenticate(UserLoginDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return "Login realizado com sucesso! Bem-vindo(a), " + user.getName();
    }
}
