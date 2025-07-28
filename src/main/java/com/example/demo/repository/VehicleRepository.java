package com.example.demo.repository;

import com.example.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    Optional<Vehicle> findByModelCode(String modelCode);
    
    List<Vehicle> findByType(String type);
    
    List<Vehicle> findByBrandName(String brandName);
    
    @Query("SELECT v FROM Vehicle v WHERE v.type = :type AND v.brandName = :brandName")
    List<Vehicle> findByTypeAndBrandName(@Param("type") String type, @Param("brandName") String brandName);
}
