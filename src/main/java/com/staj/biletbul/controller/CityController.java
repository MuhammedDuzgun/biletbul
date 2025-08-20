package com.staj.biletbul.controller;

import com.staj.biletbul.request.CreateCityRequest;
import com.staj.biletbul.response.CityResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    @PostMapping
    public ResponseEntity<CityResponse> createCity(@RequestBody CreateCityRequest request) {
        return new ResponseEntity<>(cityService.createCity(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteCityById(@PathVariable("id") Long id) {
        cityService.deleteCityById(id);
        return ResponseEntity.accepted().body(new ResourceDeletedResponse("city deleted with id: " + id));
    }

}
