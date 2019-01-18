package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * Weather Observation in a weather station based on geonames.org model.
 * Contains coordinates, observation date, temperature and station name.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObservation {

    private double lng;
    private double lat;
    @JsonAlias({"datetime"})
    @JsonDeserialize(using = DateHandler.class)
    private Date dateTime;
    private String temperature;
    private String stationName;

    private WeatherObservation(Builder builder) {
        lng = builder.lng;
        lat = builder.lat;
        dateTime = builder.dateTime;
        temperature = builder.temperature;
        stationName = builder.stationName;
    }

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

    public WeatherObservation() {
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getStationName() {
        return stationName;
    }

    public static final class Builder {
        private double lng;
        private double lat;
        private Date dateTime;
        private String temperature;
        private String stationName;

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

        public Builder dateTime(Date val) {
            dateTime = val;
            return this;
        }

        public Builder temperature(String val) {
            temperature = val;
            return this;
        }

        public Builder stationName(String val) {
            stationName = val;
            return this;
        }

        public WeatherObservation build() {
            return new WeatherObservation(this);
        }
    }
}
