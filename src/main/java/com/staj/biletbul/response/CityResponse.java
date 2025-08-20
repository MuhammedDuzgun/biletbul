package com.staj.biletbul.response;

public record CityResponse(Long id,
                           String name,
                           String country,
                           int plateNumber) {
}
