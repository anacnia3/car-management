package com.car.management.car_management.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDTO {

    @NotBlank(message = "Marca é obrigatorio")
    private String brand;

    @NotBlank(message = "Modelo é obrigatorio")
    private String model;

    @NotBlank(message = "Cor é obrigatorio")
    private String color;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1886, message = "O ano deve ser no mínimo 1886")
    @Max(value = 2030, message = "O ano deve ser um ano futuro válido (máximo 2030)")
    private Integer year;
}