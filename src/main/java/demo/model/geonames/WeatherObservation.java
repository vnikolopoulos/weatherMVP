package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObservation {

    private double lng;
    private double lat;
    @JsonAlias({"datetime"})
    @JsonDeserialize(using = DateHandler.class)
    private Date dateTime;
    private String temperature;
    private String stationName;

    @Override
    public String toString() {
        return "WeatherObservation{" +
                "lng=" + lng +
                ", lat=" + lat +
                ", dateTime=" + dateTime +
                ", temperature='" + temperature + '\'' +
                ", stationName='" + stationName + '\'' +
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
