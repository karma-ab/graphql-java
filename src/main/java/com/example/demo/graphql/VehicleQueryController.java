package com.example.demo.graphql;

import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VehicleQueryController {
    
    private final VehicleService vehicleService;

    @QueryMapping
    public List<Vehicle> vehicles(@Argument Integer count) {
        return vehicleService.getAllVehicles(count);
    }

    @QueryMapping
    public Vehicle vehicle(@Argument Long id) {
        return vehicleService.getVehicle(id).orElse(null);
    }

    @QueryMapping
    public List<Vehicle> vehiclesByType(@Argument String type) {
        return vehicleService.getVehiclesByType(type);
    }

    @QueryMapping
    public List<Vehicle> vehiclesByBrand(@Argument String brandName) {
        return vehicleService.getVehiclesByBrand(brandName);
    }
}