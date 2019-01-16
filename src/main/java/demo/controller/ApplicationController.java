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

@RestController
public class ApplicationController {

    @Autowired
    private WeatherService weatherService;

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
