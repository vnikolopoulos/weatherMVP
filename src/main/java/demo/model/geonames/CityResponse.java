package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * CityResponse class models the expected response from geonames.org for JSON city queries.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityResponse {
    List<City> geonames;

    @Override
    public String toString() {
        return "CityResponse{" +
                "geonames=" + geonames +
                '}';
    }

    public List<City> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<City> geonames) {
        this.geonames = geonames;
    }
}
