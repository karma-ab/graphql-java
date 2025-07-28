package com.example.demo.graphql;

import com.example.demo.entity.Vehicle;
import com.example.demo.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VehicleMutationController {
    
    private final VehicleService vehicleService;

    @MutationMapping
    public Vehicle createVehicle(@Argument String type, 
                                @Argument String modelCode, 
                                @Argument String brandName, 
                                @Argument String launchDate) {
        return vehicleService.createVehicle(type, modelCode, brandName, launchDate);
    }

    @MutationMapping
    public Vehicle updateVehicle(@Argument Long id, 
                                @Argument String type, 
                                @Argument String modelCode, 
                                @Argument String brandName, 
                                @Argument String launchDate) {
        return vehicleService.updateVehicle(id, type, modelCode, brandName, launchDate);
    }

    @MutationMapping
    public Boolean deleteVehicle(@Argument Long id) {
        return vehicleService.deleteVehicle(id);
    }
}