package com.car.management.car_management.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "cars",
        indexes = {
                @Index(name = "idx_cars_brand", columnList = "brand"),
                @Index(name = "idx_cars_color", columnList = "color"),
                @Index(name = "idx_cars_year", columnList = "year")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private String color;
    private Integer year;

    // 🆕 Data de criação automática para a listagem
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
