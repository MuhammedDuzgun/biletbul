package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Venue;
import com.staj.biletbul.request.CreateVenueRequest;
import com.staj.biletbul.response.VenueResponse;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {

    public VenueResponse mapToVenueResponse(Venue venue) {
        VenueResponse response = new VenueResponse(
                venue.getId(),
                venue.getName(),
                venue.getPhoneNumber(),
                venue.getAddress(),
                venue.getCity().getName()
        );
        return response;
    }

    public Venue mapToVenue(CreateVenueRequest request) {
        Venue venue = new Venue();
        venue.setName(request.venueName());
        venue.setPhoneNumber(request.phoneNumber());
        venue.setAddress(request.address());
        return venue;
    }

}
