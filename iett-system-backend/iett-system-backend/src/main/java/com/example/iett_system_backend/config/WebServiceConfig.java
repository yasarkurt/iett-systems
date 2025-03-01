package com.example.iett_system_backend.config;

import com.example.iett_system_backend.soap.bus.BusData;
import com.example.iett_system_backend.soap.bus.GetLocationRequest;
import com.example.iett_system_backend.soap.bus.GetLocationResponse;
import com.example.iett_system_backend.soap.bus.GetLocationResult;
import com.example.iett_system_backend.soap.garage.GarageData;
import com.example.iett_system_backend.soap.garage.GetGarageListRequest;
import com.example.iett_system_backend.soap.garage.GetGarageListResponse;
import com.example.iett_system_backend.soap.garage.GetGarageListResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WebServiceConfig {

    @Bean
    public Jaxb2Marshaller busMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        // Sınıf yolları kullanarak yapılandırma (ClassesToBeBound)
        marshaller.setClassesToBeBound(
                GetLocationRequest.class,
                GetLocationResponse.class,
                GetLocationResult.class,
                BusData.class
        );

        return marshaller;
    }

    @Bean
    public Jaxb2Marshaller garageMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

        // Sınıf yolları kullanarak yapılandırma (ClassesToBeBound)
        marshaller.setClassesToBeBound(
                GetGarageListRequest.class,
                GetGarageListResponse.class,
                GetGarageListResult.class,
                GarageData.class
        );

        return marshaller;
    }
}