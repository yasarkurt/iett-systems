package com.example.iett_system_backend.config;

import com.example.iett_system_backend.model.Bus;
import com.example.iett_system_backend.model.Garage;
import com.example.iett_system_backend.repository.BusRepository;
import com.example.iett_system_backend.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Configuration
public class DataLoader {

    private final Random random = new Random();

    @Bean
    public CommandLineRunner initDatabase(
            @Autowired BusRepository busRepository,
            @Autowired GarageRepository garageRepository
    ) {
        return args -> {
            // Veritabanı boşsa dummy data ekle
            if (busRepository.count() == 0 || garageRepository.count() == 0) {
                loadDummyGarages(garageRepository);
                loadDummyBuses(busRepository, garageRepository);
                System.out.println("Dummy data başarıyla yüklendi!");
            }
        };
    }

    private void loadDummyGarages(GarageRepository garageRepository) {
        // İstanbul'da bulunan gerçek garaj lokasyonlarına yakın dummy veriler
        String[] garageNames = {
                "İkitelli Garajı", "Edirnekapı Garajı", "Anadolu Garajı", "Hasanpaşa Garajı",
                "Kağıthane Garajı", "Sarıgazi Garajı", "Beylikdüzü Garajı", "Pendik Garajı",
                "Ataşehir Garajı", "Beykoz Garajı", "Avcılar Garajı", "Kadıköy Garajı",
                "Mecidiyeköy Garajı", "Çekmeköy Garajı", "Başakşehir Garajı", "Sancaktepe Garajı",
                "Bakırköy Garajı", "Büyükçekmece Garajı", "Eyüpsultan Garajı", "Beşiktaş Garajı"
        };

        String[] garageCodes = {
                "IKT", "EDK", "AND", "HSP", "KGT", "SRG", "BYL", "PND",
                "ATS", "BYK", "AVC", "KDK", "MCD", "CKM", "BSK", "SCK",
                "BKR", "BKC", "EYP", "BSK"
        };

        // İstanbul sınırları içinde yaklaşık koordinatlar
        double minLat = 40.8, maxLat = 41.2;  // İstanbul'un yaklaşık enlem sınırları
        double minLon = 28.4, maxLon = 29.4;  // İstanbul'un yaklaşık boylam sınırları

        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < garageNames.length; i++) {
            Garage garage = new Garage();
            garage.setGarageId("G" + (i + 1000));
            garage.setGarageName(garageNames[i]);
            garage.setGarageCode(garageCodes[i]);

            // Rastgele koordinatlar (İstanbul sınırları içinde)
            garage.setLatitude(minLat + (maxLat - minLat) * random.nextDouble());
            garage.setLongitude(minLon + (maxLon - minLon) * random.nextDouble());

            garage.setCreatedAt(now);
            garage.setUpdatedAt(now);

            garageRepository.save(garage);
        }
    }

    private void loadDummyBuses(BusRepository busRepository, GarageRepository garageRepository) {
        String[] operators = {"IETT", "ÖHO", "OTOBÜS A.Ş."};
        String[] platePatterns = {"34", "34"};  // İstanbul plaka kodu
        String[] plateLetters = {"AAA", "BBB", "CCC", "DDD", "EEE", "FFF", "GGG", "HHH"};

        // Garaj isimlerini al
        List<Garage> garages = garageRepository.findAll();
        String[] garageNames = garages.stream().map(Garage::getGarageName).toArray(String[]::new);

        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 100; i++) {  // 100 otobüs ekle
            Bus bus = new Bus();

            // Rastgele plaka oluştur
            String plateNumber = platePatterns[random.nextInt(platePatterns.length)] + " " +
                    plateLetters[random.nextInt(plateLetters.length)] + " " +
                    (100 + random.nextInt(900));  // Rastgele 3 basamaklı sayı

            bus.setPlateNumber(plateNumber);
            bus.setOperator(operators[random.nextInt(operators.length)]);

            // Rastgele bir garaj seç
            bus.setGarage(garageNames[random.nextInt(garageNames.length)]);

            // Kapı numarası (1-9999 arası)
            bus.setDoorNumber(String.valueOf(1 + random.nextInt(9999)));

            // Rastgele zaman (son 24 saat içinde)
            bus.setTime(now.minusHours(random.nextInt(24)).minusMinutes(random.nextInt(60)));

            // Rastgele konum (garajların yakınında)
            Garage nearbyGarage = garages.get(random.nextInt(garages.size()));
            double baseLat = nearbyGarage.getLatitude();
            double baseLon = nearbyGarage.getLongitude();

            // Garajın yaklaşık 5km yarıçapında rastgele bir nokta
            double radiusInDegrees = 0.045;  // Yaklaşık 5km
            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Konumu hesapla
            bus.setLatitude(baseLat + y);
            bus.setLongitude(baseLon + x);

            // Rastgele hız (0-80 km/s arası)
            bus.setSpeed(random.nextDouble() * 80);

            bus.setCreatedAt(now);
            bus.setUpdatedAt(now);

            busRepository.save(bus);
        }
    }
}