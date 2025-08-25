package com.staj.biletbul.response;

import java.util.List;

public record AllVenuesOfCityResponse(Long id,
                                      String name,
                                      String country,
                                      int plateNumber,
                                      List<VenueResponse> venues) {
}
