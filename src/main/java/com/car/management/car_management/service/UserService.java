package com.car.management.car_management.service;

import com.car.management.car_management.dto.request.UserRequestDTO;
import com.car.management.car_management.dto.response.UserResponseDTO;
import com.car.management.car_management.entity.User;
import com.car.management.car_management.mapper.UserMapper;
import com.car.management.car_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public UserResponseDTO create(UserRequestDTO dto) {

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já registrado");
        }

        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.toResponse(repository.save(user));
    }


    public List<UserResponseDTO> list() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }


    public UserResponseDTO findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toResponse(user);
    }


    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        repository.deleteById(id);
    }
}
