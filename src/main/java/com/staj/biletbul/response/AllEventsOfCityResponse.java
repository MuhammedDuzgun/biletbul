package com.staj.biletbul.response;

import java.util.List;

public record AllEventsOfCityResponse(Long id,
                                      String cityName,
                                      String country,
                                      int plateNumber,
                                      List<EventResponse> events) {
}
