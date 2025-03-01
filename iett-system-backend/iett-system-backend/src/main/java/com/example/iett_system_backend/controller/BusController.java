package com.example.iett_system_backend.controller;

import com.example.iett_system_backend.dto.BusDTO;
import com.example.iett_system_backend.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
@Tag(name = "Bus Controller", description = "Endpoints for managing buses")
@CrossOrigin(origins = "*")
public class BusController {

    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    @Operation(summary = "Get all buses", description = "Retrieves all buses from the IETT SOAP service and database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bus list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<BusDTO>> getAllBuses() {
        List<BusDTO> buses = busService.getAllBuses();
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/search")
    @Operation(summary = "Search and filter buses", description = "Searches and filters buses based on criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered buses"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<BusDTO>> searchBuses(
            @Parameter(description = "Operator to filter by") @RequestParam(required = false) String operator,
            @Parameter(description = "Garage to filter by") @RequestParam(required = false) String garage,
            @Parameter(description = "Door number to filter by") @RequestParam(required = false) String doorNumber,
            @Parameter(description = "Plate number to filter by") @RequestParam(required = false) String plateNumber,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        Page<BusDTO> buses = busService.getBusesWithFilters(operator, garage, doorNumber, plateNumber, page, size);
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/filter")
    @Operation(summary = "Search buses with a single term", description = "Searches buses across all relevant fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved buses"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<BusDTO>> filterBuses(
            @Parameter(description = "Search term to filter across all fields") @RequestParam String searchTerm,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        Page<BusDTO> buses = busService.searchBuses(searchTerm, page, size);
        return ResponseEntity.ok(buses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bus by ID", description = "Retrieves a specific bus by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bus",
                    content = @Content(schema = @Schema(implementation = BusDTO.class))),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BusDTO> getBusById(
            @Parameter(description = "Bus ID", required = true) @PathVariable Long id) {

        Optional<BusDTO> bus = busService.getBusById(id);
        return bus.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new bus", description = "Creates a new bus record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bus created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BusDTO> createBus(
            @Parameter(description = "Bus details", required = true) @RequestBody BusDTO busDTO) {

        BusDTO createdBus = busService.saveBus(busDTO);
        return new ResponseEntity<>(createdBus, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a bus", description = "Updates an existing bus record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bus updated successfully"),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BusDTO> updateBus(
            @Parameter(description = "Bus ID", required = true) @PathVariable Long id,
            @Parameter(description = "Updated bus details", required = true) @RequestBody BusDTO busDTO) {

        BusDTO updatedBus = busService.updateBus(id, busDTO);
        if (updatedBus != null) {
            return ResponseEntity.ok(updatedBus);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bus", description = "Deletes an existing bus record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bus deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bus not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteBus(
            @Parameter(description = "Bus ID", required = true) @PathVariable Long id) {

        boolean deleted = busService.deleteBus(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}