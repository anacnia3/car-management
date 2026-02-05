package com.car.management.car_management.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class CarResponseDTO {
    private Long id;
    private String brand;
    private String model;
    private String color;
    private Integer year;
    private LocalDateTime createdAt;
}
