package com.example.iett_system_backend.controller;

import com.example.iett_system_backend.dto.BusRequestDto;
import com.example.iett_system_backend.dto.BusResponseDto;
import com.example.iett_system_backend.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
@Tag(name = "Bus Controller", description = "APIs for managing buses")
@CrossOrigin(origins = "*")
public class BusController {
    private final BusService busService;
    private static final int DEFAULT_LIMIT = 20;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    @Operation(summary = "Get all buses", description = "Returns a list of buses, limited to 20 by default")
    public ResponseEntity<List<BusResponseDto>> getAllBuses(
            @Parameter(description = "Search term for filtering buses")
            @RequestParam(required = false) String search,
            @Parameter(description = "Maximum number of results to return")
            @RequestParam(defaultValue = "20") int limit) {

        List<BusResponseDto> buses;
        if (search != null && !search.trim().isEmpty()) {
            buses = busService.searchBuses(search.trim(), Math.min(limit, DEFAULT_LIMIT));
        } else {
            buses = busService.getAllBuses(Math.min(limit, DEFAULT_LIMIT));
        }

        return ResponseEntity.ok(buses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bus by ID", description = "Returns a single bus by its ID")
    public ResponseEntity<BusResponseDto> getBusById(
            @Parameter(description = "ID of the bus")
            @PathVariable Long id) {

        return busService.getBusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new bus", description = "Creates a new bus record")
    public ResponseEntity<BusResponseDto> createBus(
            @Parameter(description = "Bus object to be created")
            @RequestBody BusRequestDto busRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(busService.createBus(busRequestDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a bus", description = "Updates an existing bus record")
    public ResponseEntity<BusResponseDto> updateBus(
            @Parameter(description = "ID of the bus to update")
            @PathVariable Long id,
            @Parameter(description = "Updated bus object")
            @RequestBody BusRequestDto busRequestDto) {

        return busService.updateBus(id, busRequestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a bus", description = "Deletes a bus record")
    public ResponseEntity<Void> deleteBus(
            @Parameter(description = "ID of the bus to delete")
            @PathVariable Long id) {

        boolean deleted = busService.deleteBus(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}