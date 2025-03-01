package com.example.iett_system_backend.controller;

import com.example.iett_system_backend.dto.GarageDTO;
import com.example.iett_system_backend.service.GarageService;
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
@RequestMapping("/api/garages")
@Tag(name = "Garage Controller", description = "Endpoints for managing garages")
@CrossOrigin(origins = "*")
public class GarageController {

    private final GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping
    @Operation(summary = "Get all garages", description = "Retrieves all garages from the IETT SOAP service and database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved garage list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<GarageDTO>> getAllGarages() {
        List<GarageDTO> garages = garageService.getAllGarages();
        return ResponseEntity.ok(garages);
    }

    @GetMapping("/search")
    @Operation(summary = "Search and filter garages", description = "Searches and filters garages based on criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered garages"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<GarageDTO>> searchGarages(
            @Parameter(description = "Garage ID to filter by") @RequestParam(required = false) String garageId,
            @Parameter(description = "Garage name to filter by") @RequestParam(required = false) String garageName,
            @Parameter(description = "Garage code to filter by") @RequestParam(required = false) String garageCode,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        Page<GarageDTO> garages = garageService.getGaragesWithFilters(garageId, garageName, garageCode, page, size);
        return ResponseEntity.ok(garages);
    }

    @GetMapping("/filter")
    @Operation(summary = "Search garages with a single term", description = "Searches garages across all relevant fields")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved garages"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<GarageDTO>> filterGarages(
            @Parameter(description = "Search term to filter across all fields") @RequestParam String searchTerm,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        Page<GarageDTO> garages = garageService.searchGarages(searchTerm, page, size);
        return ResponseEntity.ok(garages);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get garage by ID", description = "Retrieves a specific garage by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved garage",
                    content = @Content(schema = @Schema(implementation = GarageDTO.class))),
            @ApiResponse(responseCode = "404", description = "Garage not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GarageDTO> getGarageById(
            @Parameter(description = "Garage ID", required = true) @PathVariable Long id) {

        Optional<GarageDTO> garage = garageService.getGarageById(id);
        return garage.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new garage", description = "Creates a new garage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Garage created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GarageDTO> createGarage(
            @Parameter(description = "Garage details", required = true) @RequestBody GarageDTO garageDTO) {

        GarageDTO createdGarage = garageService.saveGarage(garageDTO);
        return new ResponseEntity<>(createdGarage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a garage", description = "Updates an existing garage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Garage updated successfully"),
            @ApiResponse(responseCode = "404", description = "Garage not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<GarageDTO> updateGarage(
            @Parameter(description = "Garage ID", required = true) @PathVariable Long id,
            @Parameter(description = "Updated garage details", required = true) @RequestBody GarageDTO garageDTO) {

        GarageDTO updatedGarage = garageService.updateGarage(id, garageDTO);
        if (updatedGarage != null) {
            return ResponseEntity.ok(updatedGarage);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a garage", description = "Deletes an existing garage record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Garage deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Garage not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteGarage(
            @Parameter(description = "Garage ID", required = true) @PathVariable Long id) {

        boolean deleted = garageService.deleteGarage(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}