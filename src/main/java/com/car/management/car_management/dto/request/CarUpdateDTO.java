package com.car.management.car_management.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarUpdateDTO {

    @NotBlank(message = "A marca não pode estar em branco")
    private String brand;

    @NotBlank(message = "O modelo não pode estar em branco")
    private String model;

    @NotBlank(message = "A cor não pode estar em branco")
    private String color;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1886, message = "O ano deve ser no mínimo 1886")
    @Max(value = 2030, message = "O ano deve ser um ano futuro válido (máximo 2030)")
    private Integer year;
}