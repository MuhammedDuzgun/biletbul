package com.staj.biletbul.service;

import com.staj.biletbul.entity.City;
import com.staj.biletbul.exception.CityAlreadyExistsException;
import com.staj.biletbul.exception.CityNotFoundException;
import com.staj.biletbul.mapper.CityMapper;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.repository.CityRepository;
import com.staj.biletbul.request.CreateCityRequest;
import com.staj.biletbul.response.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final EventMapper eventMapper;

    public CityService(CityRepository cityRepository,
                       CityMapper cityMapper, EventMapper eventMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.eventMapper = eventMapper;
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

    public AllEventsOfCityResponse getAllEventsOfCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(()-> new CityNotFoundException("City not found with id " + id));

        List<EventResponse> events = city.getEventList()
                .stream()
                .map(eventMapper::mapToResponse)
                .toList();

        AllEventsOfCityResponse response = new AllEventsOfCityResponse(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getPlateNumber(),
                events
        );

        return response;
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
