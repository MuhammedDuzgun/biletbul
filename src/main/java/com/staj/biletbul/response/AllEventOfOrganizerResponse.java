package com.staj.biletbul.response;

import java.util.List;

public record AllEventOfOrganizerResponse(Long id,
                                          String organizerName,
                                          String email,
                                          List<EventResponse> events) {
}
