package com.car.management.car_management.service;

import com.car.management.car_management.dto.request.CarRequestDTO;
import com.car.management.car_management.dto.request.CarUpdateDTO;
import com.car.management.car_management.dto.response.CarResponseDTO;
import com.car.management.car_management.entity.Car;
import com.car.management.car_management.exception.ResourceNotFoundException;
import com.car.management.car_management.mapper.CarMapper;
import com.car.management.car_management.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;

    public CarResponseDTO create(CarRequestDTO dto) {
        Car car = CarMapper.toEntity(dto);
        return CarMapper.toResponse(repository.save(car));
    }

    public Page<CarResponseDTO> list(Specification<Car> spec, Pageable pageable) {
        return repository.findAll(spec, pageable)
                .map(CarMapper::toResponse);
    }

    public CarResponseDTO findById(Long id) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));
        return CarMapper.toResponse(car);
    }

    @Transactional
    public CarResponseDTO update(Long id, CarUpdateDTO dto) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found"));


        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setColor(dto.getColor());
        car.setYear(dto.getYear());

        return CarMapper.toResponse(repository.save(car));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Car not found");
        }
        repository.deleteById(id);
    }
}
