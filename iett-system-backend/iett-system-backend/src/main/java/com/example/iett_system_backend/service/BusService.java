package com.example.iett_system_backend.service;

import com.example.iett_system_backend.dto.BusDTO;
import com.example.iett_system_backend.model.Bus;
import com.example.iett_system_backend.model.Garage;
import com.example.iett_system_backend.repository.BusRepository;
import com.example.iett_system_backend.repository.GarageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusService {

    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    private final BusRepository busRepository;
    private final GarageRepository garageRepository;
    private final SoapClientService soapClientService;

    @Autowired
    public BusService(BusRepository busRepository,
                      GarageRepository garageRepository,
                      SoapClientService soapClientService) {
        this.busRepository = busRepository;
        this.garageRepository = garageRepository;
        this.soapClientService = soapClientService;
    }

    public List<BusDTO> getAllBuses() {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        List<Bus> buses = ensureDataIsFresh();

        return convertToDTOs(buses);
    }

    public Page<BusDTO> getBusesWithFilters(String operator, String garage,
                                            String doorNumber, String plateNumber,
                                            int page, int size) {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        ensureDataIsFresh();

        // Filtreleri uygula
        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> busesPage = busRepository.findBusesWithFilters(
                operator, garage, doorNumber, plateNumber, pageable);

        // DTO'lara dönüştür ve en yakın garaj bilgisini ekle
        return busesPage.map(bus -> {
            BusDTO dto = convertToDTO(bus);
            dto.setNearestGarage(findNearestGarage(bus.getLatitude(), bus.getLongitude()));
            return dto;
        });
    }

    public Page<BusDTO> searchBuses(String searchTerm, int page, int size) {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        ensureDataIsFresh();

        // Arama filtresini uygula
        Pageable pageable = PageRequest.of(page, size);
        Page<Bus> busesPage = busRepository.findBusesBySearchTerm(searchTerm, pageable);

        // DTO'lara dönüştür ve en yakın garaj bilgisini ekle
        return busesPage.map(bus -> {
            BusDTO dto = convertToDTO(bus);
            dto.setNearestGarage(findNearestGarage(bus.getLatitude(), bus.getLongitude()));
            return dto;
        });
    }

    public Optional<BusDTO> getBusById(Long id) {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        ensureDataIsFresh();

        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(b -> {
            BusDTO dto = convertToDTO(b);
            dto.setNearestGarage(findNearestGarage(b.getLatitude(), b.getLongitude()));
            return dto;
        });
    }

    public BusDTO saveBus(BusDTO busDTO) {
        Bus bus = convertToEntity(busDTO);
        Bus savedBus = busRepository.save(bus);
        BusDTO savedDto = convertToDTO(savedBus);
        savedDto.setNearestGarage(findNearestGarage(savedBus.getLatitude(), savedBus.getLongitude()));
        return savedDto;
    }

    public BusDTO updateBus(Long id, BusDTO busDTO) {
        Optional<Bus> existingBus = busRepository.findById(id);
        if (existingBus.isPresent()) {
            Bus bus = existingBus.get();

            // Alanları güncelle
            bus.setOperator(busDTO.getOperator());
            bus.setGarage(busDTO.getGarage());
            bus.setDoorNumber(busDTO.getDoorNumber());
            bus.setTime(busDTO.getTime());
            bus.setLongitude(busDTO.getLongitude());
            bus.setLatitude(busDTO.getLatitude());
            bus.setSpeed(busDTO.getSpeed());
            bus.setPlateNumber(busDTO.getPlateNumber());

            Bus savedBus = busRepository.save(bus);
            BusDTO savedDto = convertToDTO(savedBus);
            savedDto.setNearestGarage(findNearestGarage(savedBus.getLatitude(), savedBus.getLongitude()));
            return savedDto;
        }
        return null;
    }

    public boolean deleteBus(Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verilerin güncel olduğundan emin ol ve güncel verileri döndür
    private List<Bus> ensureDataIsFresh() {
        try {
            // SoapClientService içerisinde önbellekleme mantığı var
            // Eğer son çekme 1 saatten eskiyse veya hiç çekilmemişse, yeni veri çeker
            return soapClientService.fetchAndSaveBuses();
        } catch (Exception e) {
            logger.error("Otobüs verilerini güncellerken hata: {}", e.getMessage());
            // Hata durumunda mevcut verileri döndür
            return busRepository.findAll();
        }
    }

    // En yakın garajı bul
    private String findNearestGarage(Double busLat, Double busLon) {
        if (busLat == null || busLon == null) {
            return "Bilinmiyor";
        }

        List<Garage> allGarages = garageRepository.findAll();
        if (allGarages.isEmpty()) {
            return "Garaj verisi yok";
        }

        // Haversine formülü ile en yakın garajı bul
        Garage nearestGarage = allGarages.stream()
                .min(Comparator.comparing(garage ->
                        calculateDistance(busLat, busLon, garage.getLatitude(), garage.getLongitude())))
                .orElse(null);

        return nearestGarage != null ? nearestGarage.getGarageName() : "Bilinmiyor";
    }

    // Haversine mesafe formülü
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadiusKm = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadiusKm * c;
    }

    // Bus entity'sini BusDTO'ya dönüştür
    private BusDTO convertToDTO(Bus bus) {
        return new BusDTO(
                bus.getId(),
                bus.getOperator(),
                bus.getGarage(),
                bus.getDoorNumber(),
                bus.getTime(),
                bus.getLongitude(),
                bus.getLatitude(),
                bus.getSpeed(),
                bus.getPlateNumber()
        );
    }

    // Bus entity listesini BusDTO listesine dönüştür
    private List<BusDTO> convertToDTOs(List<Bus> buses) {
        return buses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // BusDTO'yu Bus entity'sine dönüştür
    private Bus convertToEntity(BusDTO dto) {
        Bus bus = new Bus();
        bus.setOperator(dto.getOperator());
        bus.setGarage(dto.getGarage());
        bus.setDoorNumber(dto.getDoorNumber());
        bus.setTime(dto.getTime());
        bus.setLongitude(dto.getLongitude());
        bus.setLatitude(dto.getLatitude());
        bus.setSpeed(dto.getSpeed());
        bus.setPlateNumber(dto.getPlateNumber());
        return bus;
    }
}