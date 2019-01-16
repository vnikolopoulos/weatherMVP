package demo.service;

import demo.exception.CountryNotFoundException;
import demo.exception.ServiceUnavailableException;
import demo.model.geonames.*;
import demo.properies.GeonamesProperties;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeonamesServiceTest {

    @InjectMocks
    GeonamesService geonamesService;

    @Mock
    RestTemplate restTemplateMock;

    @Mock
    GeonamesProperties geonamesPropertiesMock;

    CityResponse cityResponse;
    CountryInfoResponse countryInfoResponse;
    WeatherObservationResponse weatherObservationResponse;


    @BeforeAll
    void setUp() {
        CountryInfo gr;
        gr = new CountryInfo();
        gr.setNorth(0);
        gr.setSouth(0);
        gr.setEast(0);
        gr.setWest(0);
        gr.setCountryCode("GR");
        gr.setCountryName("Greece");
        countryInfoResponse = new CountryInfoResponse();
        countryInfoResponse.setGeonames(Arrays.asList(gr));

        List<City> cities = new ArrayList<>();
        City city1 = new City();
        city1.setName("Athens");
        city1.setCountryCode("GR");
        city1.setLat(39.5);
        city1.setLng(23.4);
        cities.add(city1);

        City city2 = new City();
        city2.setName("Kalamata");
        city2.setCountryCode("GR");
        city2.setLat(38);
        city2.setLng(23.2);
        cities.add(city2);

        City city3 = new City();
        city3.setName("Tirana");
        city3.setCountryCode("AL");
        city3.setLat(44);
        city3.setLng(25);
        cities.add(city3);

        City city4 = new City();
        city4.setName("Korinthos");
        city4.setCountryCode("GR");
        city4.setLat(39);
        city4.setLng(23.1);
        cities.add(city4);
        cityResponse = new CityResponse();
        cityResponse.setGeonames(cities);


        List<WeatherObservation> weatherObservations = new ArrayList<>();
        WeatherObservation wo1 = new WeatherObservation();
        wo1.setLat(39);
        wo1.setLng(23);
        wo1.setStationName("Near Athens");
        wo1.setTemperature("10");
        weatherObservations.add(wo1);

        WeatherObservation wo2 = new WeatherObservation();
        wo2.setLat(43.2);
        wo2.setLng(23.1);
        wo2.setStationName("Near Tirana");
        wo2.setTemperature("15");
        weatherObservations.add(wo2);
        weatherObservationResponse = new WeatherObservationResponse();
        weatherObservationResponse.setWeatherObservations(weatherObservations);


    }

    @BeforeEach
    void initMockito() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(CountryInfoResponse.class))).thenReturn(countryInfoResponse);
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(CityResponse.class))).thenReturn(cityResponse);
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(WeatherObservationResponse.class))).thenReturn(weatherObservationResponse);
    }

    @Test
    void getCountryInfo() {
        assertEquals(geonamesService.getCountryInfo("GR"), countryInfoResponse.getGeonames().get(0));
    }

    @Test
    void getCountryInfoServerError() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(CountryInfoResponse.class))).thenThrow(new RestClientException(""));
        assertThrows(ServiceUnavailableException.class, () -> {
            geonamesService.getCountryInfo("GR");
        });

    }

    @Test
    void getCountryInfoCountryNotFound() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(CountryInfoResponse.class))).thenReturn(null);
        assertThrows(CountryNotFoundException.class, () -> {
            geonamesService.getCountryInfo("GRA");
        });
    }

    @Test
    void getCitiesInBox() {
        List<City> cities = geonamesService.getCitiesInBox(0, 0, 0, 0);
        assertEquals(cities.size(), 4);
        assertEquals(cities, cityResponse.getGeonames());
    }

    @Test
    void getCitiesInBoxServerError() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(CityResponse.class))).thenThrow(new RestClientException(""));
        assertThrows(ServiceUnavailableException.class, () -> {
            geonamesService.getCitiesInBox(0, 0, 0, 0);
        });
    }

    @Test
    void getCitiesInBoxotFound() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(CityResponse.class))).thenReturn(null);
        assertNull(geonamesService.getCitiesInBox(0, 0, 0, 0));
    }

    @Test
    void getWeatherObservationsInBox() {
        List<WeatherObservation> weatherObservationsInBox = geonamesService.getWeatherObservationsInBox(0, 0, 0, 0);
        assertEquals(weatherObservationsInBox.size(), 2);
        assertEquals(weatherObservationsInBox, weatherObservationResponse.getWeatherObservations());

    }

    @Test
    void getWeatherObservationsInBoxServerError() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(WeatherObservationResponse.class))).thenThrow(new RestClientException(""));
        assertThrows(ServiceUnavailableException.class, () -> {
            geonamesService.getWeatherObservationsInBox(0, 0, 0, 0);
        });

    }

    @Test
    void getWeatherObservationsInBoxNotFound() {
        Mockito.when(restTemplateMock.getForObject(anyString(), eq(WeatherObservationResponse.class))).thenReturn(null);
        assertNull(geonamesService.getWeatherObservationsInBox(0, 0, 0, 0));
    }
}