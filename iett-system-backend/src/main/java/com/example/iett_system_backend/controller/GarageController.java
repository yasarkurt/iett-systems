package com.example.iett_system_backend.controller;

import com.example.iett_system_backend.dto.GarageRequestDto;
import com.example.iett_system_backend.dto.GarageResponseDto;
import com.example.iett_system_backend.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garages")
@Tag(name = "Garage Controller", description = "APIs for managing garages")
@CrossOrigin(origins = "*")
public class GarageController {
    private final GarageService garageService;
    private static final int DEFAULT_LIMIT = 20;

    @Autowired
    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping
    @Operation(summary = "Get all garages", description = "Returns a list of garages, limited to 20 by default")
    public ResponseEntity<List<GarageResponseDto>> getAllGarages(
            @Parameter(description = "Search term for filtering garages")
            @RequestParam(required = false) String search,
            @Parameter(description = "Maximum number of results to return")
            @RequestParam(defaultValue = "20") int limit) {

        List<GarageResponseDto> garages;
        if (search != null && !search.trim().isEmpty()) {
            garages = garageService.searchGarages(search.trim(), Math.min(limit, DEFAULT_LIMIT));
        } else {
            garages = garageService.getAllGarages(Math.min(limit, DEFAULT_LIMIT));
        }

        return ResponseEntity.ok(garages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get garage by ID", description = "Returns a single garage by its ID")
    public ResponseEntity<GarageResponseDto> getGarageById(
            @Parameter(description = "ID of the garage")
            @PathVariable Long id) {

        return garageService.getGarageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new garage", description = "Creates a new garage record")
    public ResponseEntity<GarageResponseDto> createGarage(
            @Parameter(description = "Garage object to be created")
            @RequestBody GarageRequestDto garageRequestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(garageService.createGarage(garageRequestDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a garage", description = "Updates an existing garage record")
    public ResponseEntity<GarageResponseDto> updateGarage(
            @Parameter(description = "ID of the garage to update")
            @PathVariable Long id,
            @Parameter(description = "Updated garage object")
            @RequestBody GarageRequestDto garageRequestDto) {

        return garageService.updateGarage(id, garageRequestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a garage", description = "Deletes a garage record")
    public ResponseEntity<Void> deleteGarage(
            @Parameter(description = "ID of the garage to delete")
            @PathVariable Long id) {

        boolean deleted = garageService.deleteGarage(id);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}