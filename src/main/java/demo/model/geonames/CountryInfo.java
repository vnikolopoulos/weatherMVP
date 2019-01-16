package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfo {
    private String countryCode;
    private String countryName;
    private double south;
    private double north;
    private double east;
    private double west;

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

    public String getCountryName() {
        return countryName;
    }

    public String getCountryCode() {
        return countryCode;
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

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public void setWest(double west) {
        this.west = west;
    }
}
