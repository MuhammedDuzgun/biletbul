package com.staj.biletbul.response;

public record VenueResponse(Long id,
                            String name,
                            String phoneNumber,
                            String address,
                            String city) {
}
