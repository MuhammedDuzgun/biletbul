package com.staj.biletbul.response;

import java.io.Serializable;

public record CityResponse(Long id,
                           String name,
                           String country,
                           int plateNumber) implements Serializable {
}
