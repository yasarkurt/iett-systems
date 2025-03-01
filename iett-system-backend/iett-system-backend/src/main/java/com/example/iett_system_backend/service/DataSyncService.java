package com.example.iett_system_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

/**
 * Veri senkronizasyonunu yönetir ve SOAP servislerinden veri çekme işlemini otomatikleştirir.
 */
@Service
public class DataSyncService {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncService.class);

    private final SoapClientService soapClientService;

    @Autowired
    public DataSyncService(SoapClientService soapClientService) {
        this.soapClientService = soapClientService;
    }

    /**
     * Uygulama başladığında verileri çeker.
     */
    @PostConstruct
    public void initializeData() {
        logger.info("Uygulama başlatıldı, SOAP verilerini çekiyor...");
        fetchAllData();
    }

    /**
     * Her saatte bir çalışarak verileri günceller.
     */
    @Scheduled(fixedRateString = "${iett.cache.hours:1}000") // 1 saatte bir
    public void scheduledDataSync() {
        logger.info("Zamanlanmış veri senkronizasyonu başlatılıyor...");
        fetchAllData();
    }

    /**
     * İhtiyaç duyulduğunda manuel olarak veri güncellemeleri için.
     */
    public void fetchAllData() {
        try {
            // Garaj verilerini çek
            logger.info("Garaj verilerini çekiyor...");
            soapClientService.fetchAndSaveGarages();

            // Otobüs verilerini çek
            logger.info("Otobüs verilerini çekiyor...");
            soapClientService.fetchAndSaveBuses();

            logger.info("SOAP veri senkronizasyonu tamamlandı.");
        } catch (Exception e) {
            logger.error("SOAP veri senkronizasyonu sırasında hata oluştu", e);
        }
    }
}