package com.car.management.car_management.dto.response;

import lombok.*;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
}
