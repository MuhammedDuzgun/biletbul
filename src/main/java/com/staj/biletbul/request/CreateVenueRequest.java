package com.staj.biletbul.request;

public record CreateVenueRequest(String venueName,
                                 String phoneNumber,
                                 String address,
                                 String cityName) {
}
