package com.staj.biletbul.response;

import java.util.List;

public record AllEventsOfUserResponse(Long id,
                                      String fullName,
                                      String email,
                                      List<EventResponse> events) {
}
