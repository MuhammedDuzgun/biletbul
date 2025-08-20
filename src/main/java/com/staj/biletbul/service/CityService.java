package com.staj.biletbul.service;

import com.staj.biletbul.entity.City;
import com.staj.biletbul.exception.CityAlreadyExistsException;
import com.staj.biletbul.exception.CityNotFoundException;
import com.staj.biletbul.mapper.CityMapper;
import com.staj.biletbul.repository.CityRepository;
import com.staj.biletbul.request.CreateCityRequest;
import com.staj.biletbul.response.CityResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityService(CityRepository cityRepository,
                       CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public List<CityResponse> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::mapToCityResponse)
                .toList();
    }

    public CityResponse getCityById(Long id) {
        return cityMapper.mapToCityResponse(cityRepository.findById(id)
                .orElseThrow(()-> new CityNotFoundException("City not found with id " + id)));
    }

    @Transactional
    public CityResponse createCity(CreateCityRequest request) {
        if (cityRepository.findByName(request.name()).isPresent()) {
            throw new CityAlreadyExistsException("city already exists with name " + request.name());
        }
        return cityMapper.mapToCityResponse(cityRepository.save(cityMapper.mapToCity(request)));
    }

    @Transactional
    public ResourceDeletedResponse deleteCityById(Long id) {
        City cityToDelete = cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException("City not found with id " + id));
        cityRepository.delete(cityToDelete);
        return new ResourceDeletedResponse("city with id " + id + " deleted successfully");
    }

}
