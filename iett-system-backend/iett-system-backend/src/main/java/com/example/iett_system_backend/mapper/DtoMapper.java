package com.example.iett_system_backend.mapper;

import com.example.iett_system_backend.dto.*;
import com.example.iett_system_backend.model.Bus;
import com.example.iett_system_backend.model.Garage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    // Bus Mappings
    public Bus toBusEntity(BusRequestDto dto) {
        Bus bus = new Bus();
        bus.setPlateNumber(dto.getPlateNumber());
        bus.setOperator(dto.getOperator());
        bus.setGarage(dto.getGarage());
        bus.setDoorNumber(dto.getDoorNumber());
        bus.setTime(dto.getTime());
        bus.setLongitude(dto.getLongitude());
        bus.setLatitude(dto.getLatitude());
        bus.setSpeed(dto.getSpeed());
        bus.setCreatedAt(LocalDateTime.now());
        bus.setUpdatedAt(LocalDateTime.now());
        return bus;
    }

    public BusResponseDto toBusResponseDto(Bus bus) {
        BusResponseDto dto = new BusResponseDto();
        dto.setId(bus.getId());
        dto.setPlateNumber(bus.getPlateNumber());
        dto.setOperator(bus.getOperator());
        dto.setGarage(bus.getGarage());
        dto.setDoorNumber(bus.getDoorNumber());
        dto.setTime(bus.getTime());
        dto.setLongitude(bus.getLongitude());
        dto.setLatitude(bus.getLatitude());
        dto.setSpeed(bus.getSpeed());
        dto.setCreatedAt(bus.getCreatedAt());
        dto.setUpdatedAt(bus.getUpdatedAt());
        return dto;
    }

    public List<BusResponseDto> toBusResponseDtoList(List<Bus> buses) {
        return buses.stream()
                .map(this::toBusResponseDto)
                .collect(Collectors.toList());
    }

    // Garage Mappings
    public Garage toGarageEntity(GarageRequestDto dto) {
        Garage garage = new Garage();
        garage.setGarageId(dto.getGarageId());
        garage.setGarageName(dto.getGarageName());
        garage.setGarageCode(dto.getGarageCode());
        garage.setLatitude(dto.getLatitude());
        garage.setLongitude(dto.getLongitude());
        garage.setCreatedAt(LocalDateTime.now());
        garage.setUpdatedAt(LocalDateTime.now());
        return garage;
    }

    public GarageResponseDto toGarageResponseDto(Garage garage) {
        GarageResponseDto dto = new GarageResponseDto();
        dto.setId(garage.getId());
        dto.setGarageId(garage.getGarageId());
        dto.setGarageName(garage.getGarageName());
        dto.setGarageCode(garage.getGarageCode());
        dto.setLatitude(garage.getLatitude());
        dto.setLongitude(garage.getLongitude());
        dto.setCreatedAt(garage.getCreatedAt());
        dto.setUpdatedAt(garage.getUpdatedAt());
        return dto;
    }

    public List<GarageResponseDto> toGarageResponseDtoList(List<Garage> garages) {
        return garages.stream()
                .map(this::toGarageResponseDto)
                .collect(Collectors.toList());
    }

    // Update entity with DTO values while preserving id and creation date
    public void updateBusFromDto(Bus bus, BusRequestDto dto) {
        bus.setPlateNumber(dto.getPlateNumber());
        bus.setOperator(dto.getOperator());
        bus.setGarage(dto.getGarage());
        bus.setDoorNumber(dto.getDoorNumber());
        bus.setTime(dto.getTime());
        bus.setLongitude(dto.getLongitude());
        bus.setLatitude(dto.getLatitude());
        bus.setSpeed(dto.getSpeed());
        bus.setUpdatedAt(LocalDateTime.now());
    }

    public void updateGarageFromDto(Garage garage, GarageRequestDto dto) {
        garage.setGarageId(dto.getGarageId());
        garage.setGarageName(dto.getGarageName());
        garage.setGarageCode(dto.getGarageCode());
        garage.setLatitude(dto.getLatitude());
        garage.setLongitude(dto.getLongitude());
        garage.setUpdatedAt(LocalDateTime.now());
    }
}