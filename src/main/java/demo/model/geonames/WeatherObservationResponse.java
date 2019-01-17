package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Weather Observation Response based on the expected response for a JSON weather in box query
 * from geonames.org.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObservationResponse {
    private List<WeatherObservation> weatherObservations;

    @Override
    public String toString() {
        return "WeatherObservationResponse{" +
                "weatherObservations=" + weatherObservations +
                '}';
    }

    public List<WeatherObservation> getWeatherObservations() {
        return weatherObservations;
    }

    public void setWeatherObservations(List<WeatherObservation> weatherObservations) {
        this.weatherObservations = weatherObservations;
    }
}
