package demo.service;

import demo.model.TemperatureReport;
import demo.model.geonames.City;
import demo.model.geonames.CountryInfo;
import demo.model.geonames.WeatherObservation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private GeonamesService geonamesServiceMock;

    List<City> cities;
    List<WeatherObservation> weatherObservations;

    @BeforeAll
    public void createCities() {
        cities = new ArrayList<>();
        City city1 = new City.Builder().name("Athens").countryCode("GR").lat(39.5).lng(23.4).build();
        cities.add(city1);

        City city2 = new City.Builder().name("Kalamata").countryCode("GR").lat(38).lng(23.2).build();
        cities.add(city2);

        City city3 = new City.Builder().name("Tirana").countryCode("AL").lat(44).lng(25).build();
        cities.add(city3);

        City city4 = new City.Builder().name("Korinthos").countryCode("GR").lat(39).lng(23.1).build();
        cities.add(city4);
    }

    @BeforeAll
    public void createWeatherObservations() {
        weatherObservations = new ArrayList<>();
        WeatherObservation wo1 = new WeatherObservation.Builder().lat(39).lng(23).stationName("Near Athens")
                .temperature("10").build();
        weatherObservations.add(wo1);

        WeatherObservation wo2 = new WeatherObservation.Builder().lat(43.2).lng(23.1).stationName("Near Tirana")
                .temperature("15").build();
        weatherObservations.add(wo2);
    }


    @Nested
    @DisplayName("Temperature For Country")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TemperatureForCountry {

        CountryInfo gr;

        @BeforeAll
        void setCountry() {
            gr = new CountryInfo.Builder().countryCode("GR").countryName("Greece").north(0).south(0).east(0).west(0).build();
        }

        @BeforeEach
        void setMockitoDefaults() {
            Mockito.when(geonamesServiceMock.getCountryInfo("GR")).thenReturn(gr);
            Mockito.when(geonamesServiceMock.getCitiesInBox(0, 0, 0, 0)).thenReturn(cities);
            Mockito.when(geonamesServiceMock.getWeatherObservationsInBox(0, 0, 0, 0)).thenReturn(weatherObservations);
        }

        @Test
        void getTemperatureForCountrySipmle() {
            TemperatureReport temperatureReport = weatherService.getTemperatureForCountry("GR");

            assertEquals(temperatureReport.getCountryName(), "Greece");
            assertEquals(temperatureReport.getCurrentTemperature().get("Near Athens"), "10");
            assertEquals(temperatureReport.getCurrentTemperature().size(), 1);
        }

        @Test
        void getTemperatreForCountryNoObservations() {
            Mockito.when(geonamesServiceMock.getWeatherObservationsInBox(0, 0, 0, 0)).thenReturn(new ArrayList<>());
            TemperatureReport temperatureReport = weatherService.getTemperatureForCountry("GR");

            assertEquals(temperatureReport.getCountryName(), "Greece");
            assertEquals(temperatureReport.getCurrentTemperature().size(), 0);
        }

        @Test
        void getTemperatreForCountryNullObservations() {
            Mockito.when(geonamesServiceMock.getWeatherObservationsInBox(0, 0, 0, 0)).thenReturn(null);
            TemperatureReport temperatureReport = weatherService.getTemperatureForCountry("GR");

            assertEquals(temperatureReport.getCountryName(), "Greece");
            assertEquals(temperatureReport.getCurrentTemperature().size(), 0);
        }

        @Test
        void getTemperatreForCountryNoCities() {
            Mockito.when(geonamesServiceMock.getCitiesInBox(0, 0, 0, 0)).thenReturn(new ArrayList<>());
            TemperatureReport temperatureReport = weatherService.getTemperatureForCountry("GR");

            assertEquals(temperatureReport.getCountryName(), "Greece");
            assertEquals(temperatureReport.getCurrentTemperature().size(), 2);
        }

        @Test
        void getTemperatreForCountryNullCities() {
            Mockito.when(geonamesServiceMock.getCitiesInBox(0, 0, 0, 0)).thenReturn(null);
            TemperatureReport temperatureReport = weatherService.getTemperatureForCountry("GR");

            assertEquals(temperatureReport.getCountryName(), "Greece");
            assertEquals(temperatureReport.getCurrentTemperature().size(), 2);
        }

    }


    @Nested
    @DisplayName("Tests for closestCity")
    class ClosestCity {

        @Test
        void closestCityClose() {
            assertEquals(weatherService.closestCity(39.4, 24.5, cities).getName(), "Athens");
        }

        @Test
        void closestCityClose2() {
            assertEquals(weatherService.closestCity(39, 23.5, cities).getName(), "Korinthos");
        }

        @Test
        void closestCityAway() {
            assertEquals(weatherService.closestCity(10, 12.3, cities).getName(), "Kalamata");
        }

    }

    @Nested
    @DisplayName("Tests for distance")
    class Distance {
        @Test
        void distanceZero() {
            assertEquals(weatherService.distance(23, 23, 39, 39, 0, 0), 0);
        }

        @Test
        void distanceSimple() {
            assertNotEquals(weatherService.distance(23, 39, 23, 39, 0, 0), 0);
        }

        @Test
        void distanceCompare() {
            assertTrue(
                    weatherService.distance(23, 24, 39, 40, 0, 0)
                            < weatherService.distance(23, 30, 39, 39, 0, 0));
        }
    }
}