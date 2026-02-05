package com.car.management.car_management.mapper;

import com.car.management.car_management.dto.request.CarRequestDTO;
import com.car.management.car_management.dto.response.CarResponseDTO;
import com.car.management.car_management.entity.Car;

import java.time.LocalDateTime;

public class CarMapper {

    public static Car toEntity(CarRequestDTO dto) {
        return Car.builder()
                .brand(dto.getBrand())
                .model(dto.getModel())
                .color(dto.getColor())
                .year(dto.getYear())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CarResponseDTO toResponse(Car car) {
        return CarResponseDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .color(car.getColor())
                .year(car.getYear())
                .createdAt(car.getCreatedAt())
                .build();
    }
}
