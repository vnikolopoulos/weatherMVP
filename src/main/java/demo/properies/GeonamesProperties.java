package demo.properies;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Configuration class about geonames.org
 */
@Configuration
@PropertySource("classpath:geonames.properties")
@ConfigurationProperties(prefix = "geonames")
@Validated
public class GeonamesProperties {

    @NotBlank
    private String url;
    @NotBlank
    private String username;
    @NotBlank
    private String countryInfo;
    @NotBlank
    private String cities;
    @NotBlank
    private String weather;
    @NotNull
    private int maxRows;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryInfo() {
        return countryInfo;
    }

    public void setCountryInfo(String countyInfo) {
        this.countryInfo = countyInfo;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }
}
