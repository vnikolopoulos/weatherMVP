package demo.model.geonames;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfoResponse {
    List<CountryInfo> geonames;

    public List<CountryInfo> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<CountryInfo> geonames) {
        this.geonames = geonames;
    }

    @Override
    public String toString() {
        return "CountryInfoResponse{" +
                "geonames=" + geonames +
                '}';
    }
}
