package com.example.demo.service;


import com.example.demo.entity.Vehicle;
import com.example.demo.repository.VehicleRepository;
import graphql.GraphQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository ;

    public VehicleService(final VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository ;
    }

    @Transactional
    public Vehicle createVehicle(final String type, final String modelCode, final String brandName, final String launchDate) {
        final Vehicle vehicle = new Vehicle();
        vehicle.setType(type);
        vehicle.setModelCode(modelCode);
        vehicle.setBrandName(brandName);
        vehicle.setLaunchDate(LocalDate.parse(launchDate));
        return this.vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles(final int count) {
        return this.vehicleRepository.findAll().stream().limit(count).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> getVehicle(final int id) {
        return this.vehicleRepository.findById(id);
    }

    public Vehicle updateVehicle(final Integer id, Optional<String> type, Optional<String> modelCode, Optional<String> brandName, Optional<String> launchDate){
        Optional<Vehicle> vehicleOptional = this.vehicleRepository.findById(id);
        if(vehicleOptional.isPresent()){
            Vehicle vehicle = vehicleOptional.get();
            vehicle.setType(type.orElse(vehicle.getType()));
            vehicle.setModelCode(modelCode.orElse(vehicle.getModelCode()));
            vehicle.setBrandName(brandName.orElse(vehicle.getBrandName()));
            vehicle.setLaunchDate(LocalDate.parse(launchDate.orElse(vehicle.getLaunchDate().toString())));
            return this.vehicleRepository.save(vehicle);
        }
        throw new GraphQLException("Could not find Vehicle with ID: "+id);
    }
}