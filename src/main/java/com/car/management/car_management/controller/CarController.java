package com.car.management.car_management.controller;

import com.car.management.car_management.dto.request.CarRequestDTO;
import com.car.management.car_management.dto.request.CarUpdateDTO;
import com.car.management.car_management.dto.response.CarResponseDTO;
import com.car.management.car_management.entity.Car;
import com.car.management.car_management.repository.specification.CarSpecification;
import com.car.management.car_management.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    private final CarService service;


    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Valid CarRequestDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return buildErrorResponse(result);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }


    @GetMapping
    public ResponseEntity<Page<CarResponseDTO>> list(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer year,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        Specification<Car> spec = Specification.where(null);
        spec = spec.and(CarSpecification.hasBrand(brand));
        spec = spec.and(CarSpecification.hasColor(color));
        spec = spec.and(CarSpecification.hasYear(year));

        return ResponseEntity.ok(service.list(spec, pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid CarUpdateDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return buildErrorResponse(result);
        }

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<List<String>> buildErrorResponse(BindingResult result) {
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}


