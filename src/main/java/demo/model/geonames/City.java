package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * City class with city name, countryCode and coordinates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    private double lng;
    private double lat;

    @JsonAlias({"countrycode"})
    private String countryCode;
    private String name;

    @Override
    public String toString() {
        return "City{" +
                "lng=" + lng +
                ", lat=" + lat +
                ", countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
