package com.staj.biletbul.response;

import java.util.List;

public record AllEventsOfVenueResponse(Long id,
                                       String venueName,
                                       String phoneNumber,
                                       String address,
                                       String city,
                                       List<EventResponse> events) {
}
