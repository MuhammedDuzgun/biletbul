package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.City;
import com.staj.biletbul.request.CreateCityRequest;
import com.staj.biletbul.response.CityResponse;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public CityResponse mapToCityResponse(City city) {
        CityResponse cityResponse = new CityResponse(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getPlateNumber()
        );
        return cityResponse;
    }

    public City mapToCity(CreateCityRequest request) {
        City city = new City();
        city.setName(request.name());
        city.setCountry(request.country());
        city.setPlateNumber(request.plateNumber());
        return city;
    }

}
