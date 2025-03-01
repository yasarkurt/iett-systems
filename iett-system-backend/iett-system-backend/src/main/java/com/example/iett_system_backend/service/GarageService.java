package com.example.iett_system_backend.service;

import com.example.iett_system_backend.dto.GarageDTO;
import com.example.iett_system_backend.model.Garage;
import com.example.iett_system_backend.repository.GarageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarageService {

    private static final Logger logger = LoggerFactory.getLogger(GarageService.class);

    private final GarageRepository garageRepository;
    private final SoapClientService soapClientService;

    @Autowired
    public GarageService(GarageRepository garageRepository, SoapClientService soapClientService) {
        this.garageRepository = garageRepository;
        this.soapClientService = soapClientService;
    }

    public List<GarageDTO> getAllGarages() {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        List<Garage> garages = ensureDataIsFresh();

        return convertToDTOs(garages);
    }

    public Page<GarageDTO> getGaragesWithFilters(String garageId, String garageName,
                                                 String garageCode, int page, int size) {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        ensureDataIsFresh();

        // Filtreleri uygula
        Pageable pageable = PageRequest.of(page, size);
        Page<Garage> garagesPage = garageRepository.findGaragesWithFilters(
                garageId, garageName, garageCode, pageable);

        return garagesPage.map(this::convertToDTO);
    }

    public Page<GarageDTO> searchGarages(String searchTerm, int page, int size) {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        ensureDataIsFresh();

        // Arama filtresini uygula
        Pageable pageable = PageRequest.of(page, size);
        Page<Garage> garagesPage = garageRepository.findGaragesBySearchTerm(searchTerm, pageable);

        return garagesPage.map(this::convertToDTO);
    }

    public Optional<GarageDTO> getGarageById(Long id) {
        // Önce SOAP servisinden verileri güncelle (gerekirse)
        ensureDataIsFresh();

        Optional<Garage> garage = garageRepository.findById(id);
        return garage.map(this::convertToDTO);
    }

    public GarageDTO saveGarage(GarageDTO garageDTO) {
        Garage garage = convertToEntity(garageDTO);
        Garage savedGarage = garageRepository.save(garage);
        return convertToDTO(savedGarage);
    }

    public GarageDTO updateGarage(Long id, GarageDTO garageDTO) {
        Optional<Garage> existingGarage = garageRepository.findById(id);
        if (existingGarage.isPresent()) {
            Garage garage = existingGarage.get();

            // Alanları güncelle
            garage.setGarageId(garageDTO.getGarageId());
            garage.setGarageName(garageDTO.getGarageName());
            garage.setGarageCode(garageDTO.getGarageCode());
            garage.setLongitude(garageDTO.getLongitude());
            garage.setLatitude(garageDTO.getLatitude());

            Garage savedGarage = garageRepository.save(garage);
            return convertToDTO(savedGarage);
        }
        return null;
    }

    public boolean deleteGarage(Long id) {
        if (garageRepository.existsById(id)) {
            garageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Verilerin güncel olduğundan emin ol ve güncel verileri döndür
    private List<Garage> ensureDataIsFresh() {
        try {
            // SoapClientService içerisinde önbellekleme mantığı var
            // Eğer son çekme 1 saatten eskiyse veya hiç çekilmemişse, yeni veri çeker
            return soapClientService.fetchAndSaveGarages();
        } catch (Exception e) {
            logger.error("Garaj verilerini güncellerken hata: {}", e.getMessage());
            // Hata durumunda mevcut verileri döndür
            return garageRepository.findAll();
        }
    }

    // Garage entity'sini GarageDTO'ya dönüştür
    private GarageDTO convertToDTO(Garage garage) {
        return new GarageDTO(
                garage.getId(),
                garage.getGarageId(),
                garage.getGarageName(),
                garage.getGarageCode(),
                garage.getLongitude(),
                garage.getLatitude()
        );
    }

    // Garage entity listesini GarageDTO listesine dönüştür
    private List<GarageDTO> convertToDTOs(List<Garage> garages) {
        return garages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GarageDTO'yu Garage entity'sine dönüştür
    private Garage convertToEntity(GarageDTO dto) {
        Garage garage = new Garage();
        garage.setGarageId(dto.getGarageId());
        garage.setGarageName(dto.getGarageName());
        garage.setGarageCode(dto.getGarageCode());
        garage.setLongitude(dto.getLongitude());
        garage.setLatitude(dto.getLatitude());
        return garage;
    }
}