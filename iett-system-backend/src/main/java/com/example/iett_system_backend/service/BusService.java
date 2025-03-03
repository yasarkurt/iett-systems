package com.example.iett_system_backend.service;

import com.example.iett_system_backend.dto.BusRequestDto;
import com.example.iett_system_backend.dto.BusResponseDto;
import com.example.iett_system_backend.mapper.DtoMapper;
import com.example.iett_system_backend.model.Bus;
import com.example.iett_system_backend.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final DtoMapper dtoMapper;

    @Autowired
    public BusService(BusRepository busRepository, DtoMapper dtoMapper) {
        this.busRepository = busRepository;
        this.dtoMapper = dtoMapper;
    }

    public List<BusResponseDto> getAllBuses(int limit) {
        List<Bus> buses = busRepository.findAll(PageRequest.of(0, limit)).getContent();
        return dtoMapper.toBusResponseDtoList(buses);
    }

    public Optional<BusResponseDto> getBusById(Long id) {
        return busRepository.findById(id)
                .map(dtoMapper::toBusResponseDto);
    }

    public List<BusResponseDto> searchBuses(String searchTerm, int limit) {
        List<Bus> buses = busRepository.findBySearchTermContainingIgnoreCase(searchTerm)
                .stream()
                .limit(limit)
                .toList();
        return dtoMapper.toBusResponseDtoList(buses);
    }

    public BusResponseDto createBus(BusRequestDto busRequestDto) {
        Bus bus = dtoMapper.toBusEntity(busRequestDto);
        Bus savedBus = busRepository.save(bus);
        return dtoMapper.toBusResponseDto(savedBus);
    }

    public Optional<BusResponseDto> updateBus(Long id, BusRequestDto busRequestDto) {
        Optional<Bus> existingBus = busRepository.findById(id);

        if (existingBus.isPresent()) {
            Bus bus = existingBus.get();
            dtoMapper.updateBusFromDto(bus, busRequestDto);
            Bus updatedBus = busRepository.save(bus);
            return Optional.of(dtoMapper.toBusResponseDto(updatedBus));
        }

        return Optional.empty();
    }

    public boolean deleteBus(Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return true;
        }
        return false;
    }
}