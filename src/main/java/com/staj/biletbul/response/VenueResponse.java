package com.staj.biletbul.response;

public record VenueResponse(Long id,
                            String venueName,
                            String phoneNumber,
                            String address,
                            String city) {
}
