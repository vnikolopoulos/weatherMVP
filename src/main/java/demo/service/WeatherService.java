package demo.service;

import demo.exception.CountryNotFoundException;
import demo.model.TemperatureReport;
import demo.model.geonames.City;
import demo.model.geonames.CountryInfo;
import demo.model.geonames.WeatherObservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Main application service, used geonamesService to find the country's bounding box, then the
 * cities and the weather stations in this box and finally finds the weather stations that are
 * actually in the country based on the closest city.
 */
@Service @CacheConfig(cacheNames = {"weather"}) public class WeatherService {

    @Autowired GeonamesService geonamesService;

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    /**
     * For the requested country, finds the boundary box, all the weather observations stations and the cities in the box.
     * Then defines the weather observation country as: The country of the city that is closest to the station.
     *
     * @param id The country id in ISO 3166-1 alpha-2 country code format
     * @return
     */
    @Cacheable public TemperatureReport getTemperatureForCountry(String id) {

        CountryInfo countryInfo = geonamesService.getCountryInfo(id);
        if (countryInfo == null) {
            throw new CountryNotFoundException("Country " + id + " not found");
        }
        TemperatureReport temperatureReport = new TemperatureReport(countryInfo.getCountryName());

        List<WeatherObservation> weatherObservationsInCountryBoundaryBox = geonamesService
            .getWeatherObservationsInBox(countryInfo.getNorth(), countryInfo.getSouth(),
                countryInfo.getEast(), countryInfo.getWest());

        if (weatherObservationsInCountryBoundaryBox == null
            || weatherObservationsInCountryBoundaryBox.isEmpty()) {
            return temperatureReport;
        }

        // These are the cities in the country's boundary box. There are not necessarily all in the country.
        // The country's borders are probably tighter.
        List<City> citiesInCountryBoundaryBox = geonamesService
            .getCitiesInBox(countryInfo.getNorth(), countryInfo.getSouth(), countryInfo.getEast(),
                countryInfo.getWest());

        //Find all weather observation stations for which the closest city is in the requested country and add them to the report
        //If there are no cities in the boundary box, return all observation stations
        for (WeatherObservation weatherObservation : weatherObservationsInCountryBoundaryBox) {
            if (citiesInCountryBoundaryBox == null || citiesInCountryBoundaryBox.isEmpty()
                || closestCity(weatherObservation.getLat(), weatherObservation.getLng(),
                citiesInCountryBoundaryBox).getCountryCode().equalsIgnoreCase(id)) {
                temperatureReport.getCurrentTemperature()
                    .put(weatherObservation.getStationName(), weatherObservation.getTemperature());
            }
        }

        if (citiesInCountryBoundaryBox != null)
            log.debug(citiesInCountryBoundaryBox.toString());
        if (weatherObservationsInCountryBoundaryBox != null)
            log.debug(weatherObservationsInCountryBoundaryBox.toString());
        log.debug(temperatureReport.toString());

        return temperatureReport;
    }

    /**
     * Find the closest city to a point defined by latitude and longitude currently using exhaustive search
     *
     * @param lat
     * @param lng
     * @param cities
     * @return
     */
    protected City closestCity(double lat, double lng, List<City> cities) {
        City closestCity = null;
        double distance = 0;
        for (City city : cities) {
            if (closestCity == null
                || distance(lat, city.getLat(), lng, city.getLng(), 0.0, 0.0) < distance) {
                closestCity = city;
                distance = distance(lat, city.getLat(), lng, city.getLng(), 0.0, 0.0);
            }
        }
        return closestCity;
    }


    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    protected double distance(double lat1, double lat2, double lon1, double lon2, double el1,
        double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math
            .sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }


}
