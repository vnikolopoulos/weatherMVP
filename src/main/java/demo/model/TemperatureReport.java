package demo.model;

import java.util.HashMap;
import java.util.Map;

public class TemperatureReport {
    String countryName;

    //Station Name -> temperature
    Map<String, String> currentTemperature;

    public TemperatureReport(String countryName) {
        this.countryName = countryName;
        this.currentTemperature = new HashMap<>();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        countryName = countryName;
    }

    public Map<String, String> getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(Map<String, String> currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    @Override
    public String toString() {
        return "TemperatureReport{" +
                "countryName='" + countryName + '\'' +
                ", currentTemperature=" + currentTemperature +
                '}';
    }
}
