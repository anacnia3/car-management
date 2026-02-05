package com.car.management.car_management.repository.specification;

import com.car.management.car_management.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    public static Specification<Car> hasBrand(String brand) {
        return (root, query, cb) -> {
            if (brand == null || brand.isBlank()) return null;

            return cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
        };
    }

    public static Specification<Car> hasColor(String color) {
        return (root, query, cb) -> {
            if (color == null || color.isBlank()) return null;


            return cb.like(cb.lower(root.get("color")), "%" + color.toLowerCase() + "%");
        };
    }

    public static Specification<Car> hasYear(Integer year) {
        return (root, query, cb) ->
                (year == null) ? null : cb.equal(root.get("year"), year);
    }
}