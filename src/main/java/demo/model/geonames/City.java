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

    private City(Builder builder) {
        lng = builder.lng;
        lat = builder.lat;
        countryCode = builder.countryCode;
        name = builder.name;
    }

    public City() {
    }

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

    public double getLat() {
        return lat;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getName() {
        return name;
    }

    public static final class Builder {
        private double lng;
        private double lat;
        private String countryCode;
        private String name;

        public Builder() {
        }

        public Builder lng(double val) {
            lng = val;
            return this;
        }

        public Builder lat(double val) {
            lat = val;
            return this;
        }

        public Builder countryCode(String val) {
            countryCode = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public City build() {
            return new City(this);
        }
    }
}
