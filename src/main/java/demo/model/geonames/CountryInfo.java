package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Country Info class contains all the information we are interested about a country
 * for JSON country info queries
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfo {
    private String countryCode;
    private String countryName;
    private double south;
    private double north;
    private double east;
    private double west;

    private CountryInfo(Builder builder) {
        countryCode = builder.countryCode;
        countryName = builder.countryName;
        south = builder.south;
        north = builder.north;
        east = builder.east;
        west = builder.west;
    }

    @Override
    public String toString() {
        return "CountryInfo{" +
                "countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", south=" + south +
                ", north=" + north +
                ", east=" + east +
                ", west=" + west +
                '}';
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getSouth() {
        return south;
    }

    public double getNorth() {
        return north;
    }

    public double getEast() {
        return east;
    }

    public double getWest() {
        return west;
    }

    public CountryInfo() {
    }


    public static final class Builder {
        private String countryCode;
        private String countryName;
        private double south;
        private double north;
        private double east;
        private double west;

        public Builder() {
        }

        public Builder countryCode(String val) {
            countryCode = val;
            return this;
        }

        public Builder countryName(String val) {
            countryName = val;
            return this;
        }

        public Builder south(double val) {
            south = val;
            return this;
        }

        public Builder north(double val) {
            north = val;
            return this;
        }

        public Builder east(double val) {
            east = val;
            return this;
        }

        public Builder west(double val) {
            west = val;
            return this;
        }

        public CountryInfo build() {
            return new CountryInfo(this);
        }
    }
}
