package com.car.management.car_management.repository;

import com.car.management.car_management.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<Car, Long>,
        JpaSpecificationExecutor<Car> {
}
