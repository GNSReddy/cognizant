package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    private static final List<Country> COUNTRIES = new ArrayList<>();

    static {
        COUNTRIES.add(new Country("US", "United States"));
        COUNTRIES.add(new Country("DE", "Germany"));
        COUNTRIES.add(new Country("IN", "India"));
        COUNTRIES.add(new Country("JP", "Japan"));
    }

    @GetMapping("/countries")
    public List<Country> getCountries() {
        LOGGER.info("Start GET /countries");
        return COUNTRIES;
    }
}
