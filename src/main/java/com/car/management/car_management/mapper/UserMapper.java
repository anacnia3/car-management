package com.car.management.car_management.mapper;

import com.car.management.car_management.dto.request.UserRequestDTO;
import com.car.management.car_management.dto.response.UserResponseDTO;
import com.car.management.car_management.entity.User;

public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(User.Role.ROLE_CLIENT)
                .build();
    }

    public static UserResponseDTO toResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
