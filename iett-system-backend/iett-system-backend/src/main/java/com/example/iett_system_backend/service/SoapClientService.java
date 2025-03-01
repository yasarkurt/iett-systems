package com.example.iett_system_backend.service;

import com.example.iett_system_backend.model.Bus;
import com.example.iett_system_backend.model.Garage;
import com.example.iett_system_backend.model.SoapFetchLog;
import com.example.iett_system_backend.repository.BusRepository;
import com.example.iett_system_backend.repository.GarageRepository;
import com.example.iett_system_backend.repository.SoapFetchLogRepository;
import com.example.iett_system_backend.soap.bus.GetLocationRequest;
import com.example.iett_system_backend.soap.bus.GetLocationResponse;
import com.example.iett_system_backend.soap.garage.GetGarageListRequest;
import com.example.iett_system_backend.soap.garage.GetGarageListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SoapClientService {

    private static final Logger logger = LoggerFactory.getLogger(SoapClientService.class);
    private static final String BUS_SERVICE_TYPE = "BUS";
    private static final String GARAGE_SERVICE_TYPE = "GARAGE";
    private static final int CACHE_HOURS = 1; // 1 hour cache as per requirement

    // IETT SOAP Service Endpoints
    private static final String ROUTE_SERVICE_ENDPOINT = "https://api.ibb.gov.tr/iett/UlasimAnaVeri/HatDurakGuzergah.asmx";
    private static final String TRIP_SERVICE_ENDPOINT = "https://api.ibb.gov.tr/iett/FiloDurum/SeferGerceklesme.asmx";

    // SOAP Actions
    private static final String GET_GARAGES_ACTION = "http://tempuri.org/GetGarageList";
    private static final String GET_BUSES_ACTION = "http://tempuri.org/GetLocation";

    // XML Namespace
    private static final String NAMESPACE_URI = "http://tempuri.org/";

    private final WebServiceTemplate busWebServiceTemplate;
    private final WebServiceTemplate garageWebServiceTemplate;
    private final BusRepository busRepository;
    private final GarageRepository garageRepository;
    private final SoapFetchLogRepository soapFetchLogRepository;

    @Autowired
    public SoapClientService(
            BusRepository busRepository,
            GarageRepository garageRepository,
            SoapFetchLogRepository soapFetchLogRepository) {
        this.busRepository = busRepository;
        this.garageRepository = garageRepository;
        this.soapFetchLogRepository = soapFetchLogRepository;

        // Setup for Bus SOAP client (SeferGerceklesme WSDL)
        Jaxb2Marshaller busMarshaller = new Jaxb2Marshaller();
        busMarshaller.setPackagesToScan("com.example.iett_system_backend.soap.bus");
        busMarshaller.setMarshallerProperties(Map.of(
                javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8",
                javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true
        ));

        this.busWebServiceTemplate = new WebServiceTemplate(busMarshaller);
        this.busWebServiceTemplate.setDefaultUri(TRIP_SERVICE_ENDPOINT);

        // Setup for Garage SOAP client (HatDurakGuzergah WSDL)
        Jaxb2Marshaller garageMarshaller = new Jaxb2Marshaller();
        garageMarshaller.setPackagesToScan("com.example.iett_system_backend.soap.garage");
        garageMarshaller.setMarshallerProperties(Map.of(
                javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8",
                javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true
        ));

        this.garageWebServiceTemplate = new WebServiceTemplate(garageMarshaller);
        this.garageWebServiceTemplate.setDefaultUri(ROUTE_SERVICE_ENDPOINT);
    }

    @Transactional
    public List<Bus> fetchAndSaveBuses() {
        try {
            // Check if we need to fetch new data based on cache time
            if (!shouldFetchData(BUS_SERVICE_TYPE)) {
                logger.info("Using cached bus data (last fetch was less than {} hour ago)", CACHE_HOURS);
                return busRepository.findAll();
            }

            logger.info("Fetching bus data from IETT SOAP service");

            // Call the IETT SeferGerceklesme SOAP service
            List<Bus> buses = fetchBusesFromSoap();

            // Save the data to the database
            if (!buses.isEmpty()) {
                busRepository.deleteAllInBatch(); // Clear existing data
                busRepository.saveAll(buses);      // Save new data

                // Log the successful fetch
                soapFetchLogRepository.save(new SoapFetchLog(BUS_SERVICE_TYPE, "SUCCESS",
                        "Fetched " + buses.size() + " buses"));
            }

            return buses;
        } catch (Exception e) {
            logger.error("Error fetching bus data from SOAP service", e);
            soapFetchLogRepository.save(new SoapFetchLog(BUS_SERVICE_TYPE, "FAILED", e.getMessage()));

            // Return existing data if available
            return busRepository.findAll();
        }
    }

    @Transactional
    public List<Garage> fetchAndSaveGarages() {
        try {
            // Check if we need to fetch
            if (!shouldFetchData(GARAGE_SERVICE_TYPE)) {
                logger.info("Using cached garage data (last fetch was less than {} hour ago)", CACHE_HOURS);
                return garageRepository.findAll();
            }

            logger.info("Fetching garage data from IETT SOAP service");

            // Call the IETT HatDurakGuzergah SOAP service
            List<Garage> garages = fetchGaragesFromSoap();

            // Save the data to the database
            if (!garages.isEmpty()) {
                garageRepository.deleteAllInBatch(); // Clear existing data
                garageRepository.saveAll(garages);    // Save new data

                // Log the successful fetch
                soapFetchLogRepository.save(new SoapFetchLog(GARAGE_SERVICE_TYPE, "SUCCESS",
                        "Fetched " + garages.size() + " garages"));
            }

            return garages;
        } catch (Exception e) {
            logger.error("Error fetching garage data from SOAP service", e);
            soapFetchLogRepository.save(new SoapFetchLog(GARAGE_SERVICE_TYPE, "FAILED", e.getMessage()));

            // Return existing data if available
            return garageRepository.findAll();
        }
    }

    private boolean shouldFetchData(String serviceType) {
        // If no successful fetch exists, we should fetch
        Optional<SoapFetchLog> latestFetch = soapFetchLogRepository.findLatestSuccessfulFetch(serviceType);
        if (latestFetch.isEmpty()) {
            return true;
        }

        // Check if the latest fetch is older than the cache threshold (1 hour)
        LocalDateTime cacheThreshold = LocalDateTime.now().minusHours(CACHE_HOURS);
        return latestFetch.get().getFetchTime().isBefore(cacheThreshold);
    }

    private List<Bus> fetchBusesFromSoap() {
        List<Bus> buses = new ArrayList<>();

        try {
            // Create request
            GetLocationRequest request = new GetLocationRequest();

            // Create QName for the request
            QName requestQName = new QName(NAMESPACE_URI, "GetLocation");

            // Call the SOAP service
            GetLocationResponse response = (GetLocationResponse) busWebServiceTemplate.marshalSendAndReceive(
                    TRIP_SERVICE_ENDPOINT,
                    request,
                    new SoapActionCallback(GET_BUSES_ACTION)
            );

            if (response != null && response.getGetLocationResult() != null) {
                String xmlResponse = response.getGetLocationResult().getAny();

                // Parse the XML response
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

                // Extract bus data
                NodeList busList = document.getElementsByTagName("Bus");
                for (int i = 0; i < busList.getLength(); i++) {
                    Element busElement = (Element) busList.item(i);

                    String operator = getElementTextContent(busElement, "Operator");
                    String garage = getElementTextContent(busElement, "Garage");
                    String doorNumber = getElementTextContent(busElement, "DoorNumber");
                    String timeStr = getElementTextContent(busElement, "Time");
                    String longitudeStr = getElementTextContent(busElement, "Longitude");
                    String latitudeStr = getElementTextContent(busElement, "Latitude");
                    String speedStr = getElementTextContent(busElement, "Speed");
                    String plateNumber = getElementTextContent(busElement, "PlateNumber");

                    // Parse values
                    LocalDateTime time = parseDateTime(timeStr);
                    Double longitude = parseDouble(longitudeStr);
                    Double latitude = parseDouble(latitudeStr);
                    Double speed = parseDouble(speedStr);

                    // Create Bus entity
                    Bus bus = new Bus(operator, garage, doorNumber, time, longitude, latitude, speed, plateNumber);
                    buses.add(bus);
                }
            }

            return buses;
        } catch (Exception e) {
            logger.error("Error processing SOAP response for buses", e);
            throw new RuntimeException("Failed to fetch bus data: " + e.getMessage(), e);
        }
    }

    private List<Garage> fetchGaragesFromSoap() {
        List<Garage> garages = new ArrayList<>();

        try {
            // Create request
            GetGarageListRequest request = new GetGarageListRequest();

            // Create QName for the request
            QName requestQName = new QName(NAMESPACE_URI, "GetGarageList");

            // Call the SOAP service
            GetGarageListResponse response = (GetGarageListResponse) garageWebServiceTemplate.marshalSendAndReceive(
                    ROUTE_SERVICE_ENDPOINT,
                    request,
                    new SoapActionCallback(GET_GARAGES_ACTION)
            );

            if (response != null && response.getGetGarageListResult() != null) {
                String xmlResponse = response.getGetGarageListResult().getAny();

                // Parse the XML response
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

                // Extract garage data
                NodeList garageList = document.getElementsByTagName("Garage");
                for (int i = 0; i < garageList.getLength(); i++) {
                    Element garageElement = (Element) garageList.item(i);

                    String garageId = getElementTextContent(garageElement, "GarageId");
                    String garageName = getElementTextContent(garageElement, "GarageName");
                    String garageCode = getElementTextContent(garageElement, "GarageCode");
                    String longitudeStr = getElementTextContent(garageElement, "Longitude");
                    String latitudeStr = getElementTextContent(garageElement, "Latitude");

                    // Parse values
                    Double longitude = parseDouble(longitudeStr);
                    Double latitude = parseDouble(latitudeStr);

                    // Create Garage entity
                    Garage garage = new Garage(garageId, garageName, garageCode, longitude, latitude);
                    garages.add(garage);
                }
            }

            return garages;
        } catch (Exception e) {
            logger.error("Error processing SOAP response for garages", e);
            throw new RuntimeException("Failed to fetch garage data: " + e.getMessage(), e);
        }
    }

    // Helper method to get text content of an element
    private String getElementTextContent(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    // Helper method to parse date time
    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            return LocalDateTime.now(); // Default to current time if parsing fails
        }
    }

    // Helper method to parse double
    private Double parseDouble(String doubleStr) {
        try {
            return Double.parseDouble(doubleStr);
        } catch (Exception e) {
            return 0.0; // Default to 0 if parsing fails
        }
    }
}