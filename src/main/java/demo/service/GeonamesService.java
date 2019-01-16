package demo.service;

import demo.exception.CountryNotFoundException;
import demo.exception.ServiceUnavailableException;
import demo.model.geonames.*;
import demo.properies.GeonamesProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@EnableConfigurationProperties(GeonamesProperties.class)
public class GeonamesService {

    @Autowired
    private GeonamesProperties geonamesProperties;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(GeonamesService.class);

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * Returns the country info for the requested country id
     *
     * @param id The country id in ISO 3166-1 alpha-2 country code format
     * @return country information
     */
    public CountryInfo getCountryInfo(String id) {
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(geonamesProperties.getUrl()).append(geonamesProperties.getCountryInfo()).append("?country=")
                .append(id).append("&username=").append(geonamesProperties.getUsername());
        log.debug(requestURL.toString());
        CountryInfoResponse countryInfoResponse;
        try {
            countryInfoResponse = restTemplate.getForObject(requestURL.toString(), CountryInfoResponse.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("Error with: " + geonamesProperties.getUrl());
        }

        if (countryInfoResponse == null || countryInfoResponse.getGeonames().size() != 1) {
            throw new CountryNotFoundException("Country " + id + " not found");
        }
        log.debug(countryInfoResponse.toString());

        CountryInfo country = countryInfoResponse.getGeonames().get(0);
        Assert.isTrue(country.getCountryCode().equalsIgnoreCase(id), "Country Code does not match ");

        return country;
    }

    /**
     * Returns a List of cities in the selected, square, bounding box, defined by 2 points (north-west & south-east)
     *
     * @param north coordinate
     * @param south coordinate
     * @param east  coordinate
     * @param west  coordinate
     * @return
     */
    public List<City> getCitiesInBox(double north, double south, double east, double west) {
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(geonamesProperties.getUrl()).append(geonamesProperties.getCities()).append("?north=")
                .append(north).append("&south=").append(south).append("&east=").append(east).append("&west=").append(west)
                .append("&maxRows=").append(geonamesProperties.getMaxRows()).append("&username=")
                .append(geonamesProperties.getUsername());
        log.debug(requestURL.toString());
        CityResponse cityResponse;
        try {
            cityResponse = restTemplate.getForObject(requestURL.toString(), CityResponse.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("Error with: " + geonamesProperties.getUrl());
        }
        if (cityResponse == null) {
            return null;
        }
        return cityResponse.getGeonames();

    }

    /**
     * Returns a List of weather observation in the selected, square, bounding box, defined by 2 points (north-west & south-east)
     *
     * @param north coordinate
     * @param south coordinate
     * @param east  coordinate
     * @param west  coordinate
     * @return
     */
    public List<WeatherObservation> getWeatherObservationsInBox(double north, double south, double east, double west) {
        StringBuilder requestURL = new StringBuilder();
        requestURL.append(geonamesProperties.getUrl()).append(geonamesProperties.getWeather()).append("?north=")
                .append(north).append("&south=").append(south).append("&east=").append(east).append("&west=").append(west)
                .append("&maxRows=").append(geonamesProperties.getMaxRows()).append("&username=")
                .append(geonamesProperties.getUsername());
        log.debug(requestURL.toString());
        WeatherObservationResponse weatherObservationResponse;
        try {
            weatherObservationResponse = restTemplate.getForObject(requestURL.toString(), WeatherObservationResponse.class);
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("Error with: " + geonamesProperties.getUrl());
        }
        if (weatherObservationResponse == null) {
            return null;
        }
        return weatherObservationResponse.getWeatherObservations();
    }
}
