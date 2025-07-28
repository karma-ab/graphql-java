package com.example.demo;

import com.example.demo.entity.Vehicle;
import com.example.demo.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("dev")
@Transactional
class VehicleGraphQLTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        vehicleRepository.deleteAll();
    }

    @Test
    void shouldCreateVehicle() {
        String mutation = """
            mutation {
                createVehicle(
                    type: "SUV"
                    modelCode: "TEST001"
                    brandName: "TestBrand"
                    launchDate: "2023-01-01"
                ) {
                    id
                    type
                    modelCode
                    brandName
                    launchDate
                }
            }
            """;

        graphQlTester.document(mutation)
                .execute()
                .path("createVehicle")
                .entity(Vehicle.class)
                .satisfies(vehicle -> {
                    assert vehicle.getType().equals("SUV");
                    assert vehicle.getModelCode().equals("TEST001");
                    assert vehicle.getBrandName().equals("TestBrand");
                });
    }

    @Test
    void shouldGetAllVehicles() {
        // Create test data
        Vehicle vehicle1 = new Vehicle(null, "SUV", "TEST001", "Brand1", LocalDate.of(2023, 1, 1));
        Vehicle vehicle2 = new Vehicle(null, "Sedan", "TEST002", "Brand2", LocalDate.of(2023, 2, 1));
        vehicleRepository.save(vehicle1);
        vehicleRepository.save(vehicle2);

        String query = """
            query {
                vehicles {
                    id
                    type
                    modelCode
                    brandName
                }
            }
            """;

        graphQlTester.document(query)
                .execute()
                .path("vehicles")
                .entityList(Vehicle.class)
                .hasSize(2);
    }

    @Test
    void shouldGetVehicleById() {
        Vehicle savedVehicle = vehicleRepository.save(
                new Vehicle(null, "SUV", "TEST001", "Brand1", LocalDate.of(2023, 1, 1))
        );

        String query = """
            query($id: ID!) {
                vehicle(id: $id) {
                    id
                    type
                    modelCode
                    brandName
                }
            }
            """;

        graphQlTester.document(query)
                .variable("id", savedVehicle.getId())
                .execute()
                .path("vehicle")
                .entity(Vehicle.class)
                .satisfies(vehicle -> {
                    assert vehicle.getId().equals(savedVehicle.getId());
                    assert vehicle.getType().equals("SUV");
                });
    }

    @Test
    void shouldUpdateVehicle() {
        Vehicle savedVehicle = vehicleRepository.save(
                new Vehicle(null, "SUV", "TEST001", "Brand1", LocalDate.of(2023, 1, 1))
        );

        String mutation = """
            mutation($id: ID!) {
                updateVehicle(
                    id: $id
                    type: "Sedan"
                    brandName: "UpdatedBrand"
                ) {
                    id
                    type
                    modelCode
                    brandName
                }
            }
            """;

        graphQlTester.document(mutation)
                .variable("id", savedVehicle.getId())
                .execute()
                .path("updateVehicle")
                .entity(Vehicle.class)
                .satisfies(vehicle -> {
                    assert vehicle.getType().equals("Sedan");
                    assert vehicle.getBrandName().equals("UpdatedBrand");
                    assert vehicle.getModelCode().equals("TEST001"); // Should remain unchanged
                });
    }

    @Test
    void shouldDeleteVehicle() {
        Vehicle savedVehicle = vehicleRepository.save(
                new Vehicle(null, "SUV", "TEST001", "Brand1", LocalDate.of(2023, 1, 1))
        );

        String mutation = """
            mutation($id: ID!) {
                deleteVehicle(id: $id)
            }
            """;

        graphQlTester.document(mutation)
                .variable("id", savedVehicle.getId())
                .execute()
                .path("deleteVehicle")
                .entity(Boolean.class)
                .isEqualTo(true);

        // Verify vehicle is deleted
        assert vehicleRepository.findById(savedVehicle.getId()).isEmpty();
    }
}