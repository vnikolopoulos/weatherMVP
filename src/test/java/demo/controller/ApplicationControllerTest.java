package demo.controller;

import demo.exception.CountryNotFoundException;
import demo.model.TemperatureReport;
import demo.service.WeatherService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationControllerTest {

    @InjectMocks
    ApplicationController applicationController;

    @Mock
    WeatherService weatherService;

    @BeforeEach
    void initMock() {
        Mockito.when(weatherService.getTemperatureForCountry(anyString())).thenReturn(new TemperatureReport(""));
    }

    @Test
    void getTemperatureInCountryCorrect() {
        assertNotNull(applicationController.getTemperatureInCountry("gr"));
    }

    @Test
    void getTemperatureInCountryNotISO() {
        assertThrows(CountryNotFoundException.class, () -> {
            applicationController.getTemperatureInCountry("notvalid");
        });
    }
}