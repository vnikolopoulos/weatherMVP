package demo.controller;

import demo.exception.CountryNotFoundException;
import demo.model.TemperatureReport;
import demo.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Weather Application controller, returns the latest reported temperatures for all the weather
 * stations in a country. The country code should be ISO3166 compliant (a check is performed).
 */
@RestController
public class ApplicationController {

    @Autowired
    private WeatherService weatherService;

    //Initialize a static set of all available country code in order to make fast validations.
    private static Set<String> ISO3166CountryCodes = new HashSet<>(Arrays.asList(Locale.getISOCountries()));

    @RequestMapping(value = "/{id}")
    public TemperatureReport getTemperatureInCountry(@PathVariable String id) {
        if (!ISO3166CountryCodes.contains(id.toUpperCase()))
            throw new CountryNotFoundException(id + " is not valid ISO3166 country code");
        return weatherService.getTemperatureForCountry(id);
    }

    public ApplicationController() {
    }
}
