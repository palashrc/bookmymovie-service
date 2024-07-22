package com.bookmymovie.theater.controllers;

import com.bookmymovie.theater.model.City;
import com.bookmymovie.theater.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
@Slf4j
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/city-add")
    @ResponseBody
    public void addCity(@RequestBody City city) {
        cityService.saveCity(city);
    }

    @GetMapping("/city-all")
    @ResponseBody
    public List<City> getCity() {
        return cityService.getCity();
    }

    @GetMapping("/city-by-id/{id}")
    @ResponseBody
    public City getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    @GetMapping("/city-by-name/{name}")
    @ResponseBody
    public List<City> getCityByName(@PathVariable String name) {
        return cityService.getCityByName(name);
    }
}
