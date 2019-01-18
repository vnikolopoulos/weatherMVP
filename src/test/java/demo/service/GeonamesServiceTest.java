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
        gr = new CountryInfo.Builder().countryCode("GR").countryName("Greece").north(0).south(0).east(0).west(0).build();
        countryInfoResponse = new CountryInfoResponse();
        countryInfoResponse.setGeonames(Arrays.asList(gr));

        List<City> cities = new ArrayList<>();
        City city1 = new City.Builder().name("Athens").countryCode("GR").lat(39.5).lng(23.4).build();
        cities.add(city1);

        City city2 = new City.Builder().name("Kalamata").countryCode("GR").lat(38).lng(23.2).build();
        cities.add(city2);

        City city3 = new City.Builder().name("Tirana").countryCode("AL").lat(44).lng(25).build();
        cities.add(city3);

        City city4 = new City.Builder().name("Korinthos").countryCode("GR").lat(39).lng(23.1).build();
        cities.add(city4);
        cityResponse = new CityResponse();
        cityResponse.setGeonames(cities);


        List<WeatherObservation> weatherObservations = new ArrayList<>();
        WeatherObservation wo1 = new WeatherObservation.Builder().lat(39).lng(23).stationName("Near Athens")
                .temperature("10").build();
        weatherObservations.add(wo1);

        WeatherObservation wo2 = new WeatherObservation.Builder().lat(43.2).lng(23.1).stationName("Near Tirana")
                .temperature("15").build();
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