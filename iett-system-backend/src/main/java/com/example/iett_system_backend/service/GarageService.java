package com.example.iett_system_backend.service;

import com.example.iett_system_backend.dto.GarageRequestDto;
import com.example.iett_system_backend.dto.GarageResponseDto;
import com.example.iett_system_backend.mapper.DtoMapper;
import com.example.iett_system_backend.model.Garage;
import com.example.iett_system_backend.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageService {
    private final GarageRepository garageRepository;
    private final DtoMapper dtoMapper;

    @Autowired
    public GarageService(GarageRepository garageRepository, DtoMapper dtoMapper) {
        this.garageRepository = garageRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<GarageResponseDto> getAllGarages(int limit) {
        List<Garage> garages = garageRepository.findAll(PageRequest.of(0, limit)).getContent();
        return dtoMapper.toGarageResponseDtoList(garages);
    }

    public Optional<GarageResponseDto> getGarageById(Long id) {
        return garageRepository.findById(id)
                .map(dtoMapper::toGarageResponseDto);
    }

    public List<GarageResponseDto> searchGarages(String searchTerm, int limit) {
        List<Garage> garages = garageRepository.findBySearchTermContainingIgnoreCase(searchTerm)
                .stream()
                .limit(limit)
                .toList();
        return dtoMapper.toGarageResponseDtoList(garages);
    }

    public GarageResponseDto createGarage(GarageRequestDto garageRequestDto) {
        Garage garage = dtoMapper.toGarageEntity(garageRequestDto);
        Garage savedGarage = garageRepository.save(garage);
        return dtoMapper.toGarageResponseDto(savedGarage);
    }

    public Optional<GarageResponseDto> updateGarage(Long id, GarageRequestDto garageRequestDto) {
        Optional<Garage> existingGarage = garageRepository.findById(id);

        if (existingGarage.isPresent()) {
            Garage garage = existingGarage.get();
            dtoMapper.updateGarageFromDto(garage, garageRequestDto);
            Garage updatedGarage = garageRepository.save(garage);
            return Optional.of(dtoMapper.toGarageResponseDto(updatedGarage));
        }

        return Optional.empty();
    }

    public boolean deleteGarage(Long id) {
        if (garageRepository.existsById(id)) {
            garageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}