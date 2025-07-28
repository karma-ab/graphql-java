package com.example.demo.service;

import com.example.demo.entity.Vehicle;
import com.example.demo.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public Vehicle createVehicle(String type, String modelCode, String brandName, String launchDate) {
        log.info("Creating vehicle with modelCode: {}", modelCode);
        
        // Check if vehicle with same model code already exists
        if (vehicleRepository.findByModelCode(modelCode).isPresent()) {
            throw new IllegalArgumentException("Vehicle with model code " + modelCode + " already exists");
        }
        
        Vehicle vehicle = new Vehicle();
        vehicle.setType(type);
        vehicle.setModelCode(modelCode);
        vehicle.setBrandName(brandName);
        
        if (launchDate != null && !launchDate.trim().isEmpty()) {
            vehicle.setLaunchDate(LocalDate.parse(launchDate));
        }
        
        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        log.info("Successfully created vehicle with ID: {}", savedVehicle.getId());
        return savedVehicle;
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles(Integer count) {
        log.info("Fetching vehicles with limit: {}", count);
        
        if (count == null || count <= 0) {
            return vehicleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        
        Pageable pageable = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "id"));
        return vehicleRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicle(Long id) {
        log.info("Fetching vehicle with ID: {}", id);
        return vehicleRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByType(String type) {
        log.info("Fetching vehicles by type: {}", type);
        return vehicleRepository.findByType(type);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getVehiclesByBrand(String brandName) {
        log.info("Fetching vehicles by brand: {}", brandName);
        return vehicleRepository.findByBrandName(brandName);
    }

    @Transactional
    public Vehicle updateVehicle(Long id, String type, String modelCode, String brandName, String launchDate) {
        log.info("Updating vehicle with ID: {}", id);
        
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + id));

        // Check if model code is being changed and if new model code already exists
        if (modelCode != null && !modelCode.equals(vehicle.getModelCode())) {
            if (vehicleRepository.findByModelCode(modelCode).isPresent()) {
                throw new IllegalArgumentException("Vehicle with model code " + modelCode + " already exists");
            }
            vehicle.setModelCode(modelCode);
        }

        if (type != null) {
            vehicle.setType(type);
        }
        
        if (brandName != null) {
            vehicle.setBrandName(brandName);
        }
        
        if (launchDate != null && !launchDate.trim().isEmpty()) {
            vehicle.setLaunchDate(LocalDate.parse(launchDate));
        }

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        log.info("Successfully updated vehicle with ID: {}", updatedVehicle.getId());
        return updatedVehicle;
    }

    @Transactional
    public boolean deleteVehicle(Long id) {
        log.info("Deleting vehicle with ID: {}", id);
        
        if (!vehicleRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found with ID: " + id);
        }
        
        vehicleRepository.deleteById(id);
        log.info("Successfully deleted vehicle with ID: {}", id);
        return true;
    }
}