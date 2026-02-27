package com.car.management.car_management.repository.specification;

import com.car.management.car_management.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    public static Specification<Car> hasBrand(String brand) {
        return (root, query, cb) -> {
            if (brand == null) return null;
            String normalizedBrand = brand.trim();
            if (normalizedBrand.isBlank()) return null;

            return cb.like(cb.lower(root.get("brand")), "%" + normalizedBrand.toLowerCase() + "%");
        };
    }

    public static Specification<Car> hasColor(String color) {
        return (root, query, cb) -> {
            if (color == null) return null;
            String normalizedColor = color.trim();
            if (normalizedColor.isBlank()) return null;


            return cb.like(cb.lower(root.get("color")), "%" + normalizedColor.toLowerCase() + "%");
        };
    }

    public static Specification<Car> hasYear(Integer year) {
        return (root, query, cb) ->
                (year == null) ? null : cb.equal(root.get("year"), year);
    }
}
