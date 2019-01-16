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
    }

    @BeforeAll
    public void createWeatherObservations() {
        weatherObservations = new ArrayList<>();
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
    }


    @Nested
    @DisplayName("Temperature For Country")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class TemperatureForCountry {

        CountryInfo gr;

        @BeforeAll
        void setCountry() {
            gr = new CountryInfo();
            gr.setNorth(0);
            gr.setSouth(0);
            gr.setEast(0);
            gr.setWest(0);
            gr.setCountryCode("GR");
            gr.setCountryName("Greece");
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