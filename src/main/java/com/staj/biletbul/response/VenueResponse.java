package com.staj.biletbul.response;

import java.io.Serializable;

public record VenueResponse(Long id,
                            String venueName,
                            String phoneNumber,
                            String address,
                            String city) implements Serializable {
}
