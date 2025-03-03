# IETT Sistem - Otobüs ve Garaj Yönetim Uygulaması

Bu proje, İstanbul Elektrik Tramvay ve Tünel İşletmeleri (IETT) tarafından sağlanan otobüs ve garaj verilerini yönetmek ve görselleştirmek için geliştirilmiş bir full-stack uygulamadır.

## Proje Özeti

IETT Sistem uygulaması, otobüs ve garaj verilerini yönetmek için bir API sunan Spring Boot tabanlı bir backend ve bu verileri görselleştiren React tabanlı bir frontend bileşeninden oluşur. Uygulama, verileri PostgreSQL veritabanında saklar ve Docker konteynerlerini kullanarak dağıtılabilir.

## Kullanılan Teknolojiler

### Backend
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- PostgreSQL
- Swagger/OpenAPI 3.0
- Docker

### Frontend
- React 18
- TypeScript
- React Router v6
- Leaflet (Harita Kütüphanesi)
- React Leaflet
- CSS3

## Tasarım Aşamasından Notlar

### Mimari Tasarım

Proje, modern microservices mimarisine uygun olarak katmanlı bir yapıda tasarlanmıştır:

1. **Veri Katmanı**: PostgreSQL veritabanı
2. **API Katmanı**: Spring Boot REST servisleri
3. **İstemci Katmanı**: React ve TypeScript kullanılan frontend uygulaması

### Backend Tasarımı

Backend kısmı, klasik Spring Boot mimarisi kullanılarak geliştirilmiştir:

- **Controller Katmanı**: RESTful API endpoint'lerini tanımlar
- **Service Katmanı**: İş mantığını içerir
- **Repository Katmanı**: Veritabanı erişimini sağlar
- **Model Katmanı**: Veri varlıklarını temsil eder
- **DTO Katmanı**: Veri transfer nesnelerini tanımlar
- **Mapper Katmanı**: Model ve DTO arasında dönüşüm sağlar

### Frontend Tasarımı

Frontend kısmı, modern React uygulaması olarak tasarlanmıştır:

- **Component Tabanlı Yapı**: Yeniden kullanılabilir UI bileşenleri
- **API İstemcileri**: Backend ile iletişim için servisler
- **Sayfa Bileşenleri**: Otobüs ve garaj verileri için ayrı görünümler
- **Harita Entegrasyonu**: Leaflet kütüphanesi kullanılarak harita görselleştirmesi
- **Responsive Tasarım**: Mobil ve masaüstü ekranlar için uyumlu

## Alınan Tasarım Kararları

### Backend Kararları

1. **SOAP yerine Dummy Data**: SOAP servislerine gerçek bağlantı yerine sistem başlatıldığında veritabanına otomatik olarak yüklenen dummy veriler kullanılmıştır. Bu durumun nedeni WSDL'in parse edilememesidir. 

2. **DTO Deseni**: Model ve API yanıtları arasında DTO (Data Transfer Object) deseni kullanılarak ayrım sağlanmıştır. Bu, API'nin evrimleşmesini kolaylaştırır ve iç uygulamanın yapısını dış dünyadan gizler.

3. **API Dokümantasyonu**: Swagger/OpenAPI kullanılarak API dokümantasyonu otomatik olarak oluşturulmuştur, bu da API'nin keşfedilebilirliğini ve kullanılabilirliğini artırır.

4. **Paging ve Filtering**: API'de sayfalama ve filtreleme özellikleri sunucuda gerçekleştirilmiştir, bu da istemciye minimum veri miktarı göndererek performansı artırır.

### Frontend Kararları

1. **Komponent Tabanlı Mimari**: Yeniden kullanılabilir bileşenler oluşturularak kod tekrarı azaltılmış ve bakım kolaylaştırılmıştır.

2. **Leaflet Harita Entegrasyonu**: Açık kaynaklı Leaflet kütüphanesi kullanılarak veriler harita üzerinde görselleştirilmiştir. Bu, özelleştirilebilir ve hafif bir harita çözümü sağlar.

3. **Responsive Tasarım**: Uygulama, farklı ekran boyutlarına uyum sağlayacak şekilde tasarlanmış, böylece mobil cihazlarda da kullanılabilirliği sağlanmıştır.

4. **Server-Side Filtering**: Filtreleme işlemlerinin sunucu tarafında yapılması, büyük veri setleriyle daha verimli çalışma olanağı sağlar.

5. **En Yakın Garaj Hesaplaması**: Kuş bakışı mesafeleri hesaplanmıştır, bu da her otobüs için doğru en yakın garaj bilgisinin gösterilmesini sağlar.

## Geliştirme Sırasında Yapılan Varsayımlar

1. **Veri Güncelleme Sıklığı**:Veriler sadece uygulama başlangıcında yüklenir. SOAP entegrasyonu ile bu durum düzeltilebilir.

2. **Koordinat Doğruluğu**: Tüm koordinat verilerinin geçerli olduğu ve İstanbul şehir sınırları içinde olduğu varsayılmıştır.

3. **Kullanıcı Sayısı**: Uygulamanın görece az sayıda eşzamanlı kullanıcı tarafından kullanılacağı varsayılmıştır, bu nedenle kapsamlı bir önbellek mekanizması eklenmemiştir.

4. **Güvenlik Gereksinimleri**: Bu demo versiyonunda kimlik doğrulama ve yetkilendirme (authentication & authorization) mekanizmaları dahil edilmemiştir, ancak gerçek bir üretim uygulamasında bu özellikler eklenmesi gerekecektir.

5. **Sorgulama Parametreleri**: Kullanıcıların, Garaj Kimliği, Garaj Adı, Garaj Kodu veya otobüsler için Operatör, Garaj, KapıNo ve Plaka temelli arama yapacakları varsayılmıştır.

## Projenin Çalıştırılması

### Gereksinimler

- Docker ve Docker Compose
- Git

### Docker ile Çalıştırma (Önerilen)

1. Projeyi klonlayın:

git clone https://github.com/yasarkurt/iett-systems.git
cd iett-systems

2. Docker Compose ile tüm uygulamayı başlatın:

docker-compose up --build

3. Uygulama şu adreslerde çalışacaktır:
- Frontend: http://localhost
- Backend API: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui
- PostgreSQL: localhost:5432
- PGAdmin: localhost:5050

## Project Structure
```
iett-systems/
├── docker-compose.yml
│
├── iett-system-backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/iett_system_backend/
│   │   │   │   ├── config/
│   │   │   │   ├── controller/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── model/
│   │   │   │   ├── repository/
│   │   │   │   ├── service/
│   │   │   │   └── IettSystemBackendApplication.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── Dockerfile
│   └── pom.xml
│
└── iett-system-frontend/
├── public/
├── src/
│   ├── api/
│   ├── components/
│   ├── pages/
│   ├── types/
│   ├── utils/
│   ├── App.tsx
│   └── index.tsx
├── Dockerfile
├── nginx.conf
└── package.json
```

## Özellikler ve Kısıtlamalar

### Mevcut Özellikler

- Otobüs ve garaj verilerinin listelenmesi ve filtrelenmesi
- Verilerin harita üzerinde görselleştirilmesi
- Her otobüs için en yakın garajın hesaplanması
- Resonsive tasarım

### Kısıtlamalar ve Gelecek Geliştirmeler

- Gerçek SOAP entegrasyonu eklenmesi
- Kullanıcı kimlik doğrulama ve yetkilendirme mekanizmaları


## API Endpoint'leri

### Garajlar (Garages)

| Metod | Endpoint | Açıklama | Parametreler |
|-------|----------|----------|--------------|
| GET | /api/garages | Tüm garajları listeler | search (opsiyonel): Arama terimi<br>limit (opsiyonel, default=20): Maksimum sonuç sayısı |
| GET | /api/garages/{id} | ID'ye göre tek bir garaj getirir | id: Garaj ID |
| POST | /api/garages | Yeni bir garaj oluşturur | Request Body: GarageRequestDto |
| PUT | /api/garages/{id} | Mevcut bir garajı günceller | id: Garaj ID<br>Request Body: GarageRequestDto |
| DELETE | /api/garages/{id} | Bir garajı siler | id: Garaj ID |

### Otobüsler (Buses)

| Metod | Endpoint | Açıklama | Parametreler |
|-------|----------|----------|--------------|
| GET | /api/buses | Tüm otobüsleri listeler | search (opsiyonel): Arama terimi<br>limit (opsiyonel, default=20): Maksimum sonuç sayısı |
| GET | /api/buses/{id} | ID'ye göre tek bir otobüs getirir | id: Otobüs ID |
| POST | /api/buses | Yeni bir otobüs oluşturur | Request Body: BusRequestDto |
| PUT | /api/buses/{id} | Mevcut bir otobüsü günceller | id: Otobüs ID<br>Request Body: BusRequestDto |
| DELETE | /api/buses/{id} | Bir otobüsü siler | id: Otobüs ID |

Swagger UI ile tüm API'lerin detaylı dokümantasyonuna şu adresten erişebilirsiniz: http://localhost:8081/swagger-ui