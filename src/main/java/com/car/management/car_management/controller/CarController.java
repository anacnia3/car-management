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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.Year;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CarController {

    private static final int MIN_CAR_YEAR = 1886;
    private static final int MAX_PAGE_SIZE = 100;

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
    public ResponseEntity<?> list(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Integer year,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        if (!isValidCarYear(year)) {
            int maxCarYear = Year.now().getValue() + 1;
            return buildErrorResponse(List.of(
                    "year: deve estar entre " + MIN_CAR_YEAR + " e " + maxCarYear
            ));
        }

        Pageable normalizedPageable = normalizePageable(pageable);

        Specification<Car> spec = Specification.where(null);
        spec = spec.and(CarSpecification.hasBrand(brand));
        spec = spec.and(CarSpecification.hasColor(color));
        spec = spec.and(CarSpecification.hasYear(year));

        return ResponseEntity.ok(service.list(spec, normalizedPageable));
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

    private ResponseEntity<List<String>> buildErrorResponse(List<String> errors) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    private boolean isValidCarYear(Integer year) {
        if (year == null) {
            return true;
        }
        int maxCarYear = Year.now().getValue() + 1;
        return year >= MIN_CAR_YEAR && year <= maxCarYear;
    }

    private Pageable normalizePageable(Pageable pageable) {
        int pageSize = Math.min(pageable.getPageSize(), MAX_PAGE_SIZE);
        return PageRequest.of(pageable.getPageNumber(), pageSize, pageable.getSort());
    }
}


