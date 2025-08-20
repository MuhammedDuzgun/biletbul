package com.staj.biletbul.request;

public record CreateCityRequest(String name,
                                String country,
                                int plateNumber) {
}
