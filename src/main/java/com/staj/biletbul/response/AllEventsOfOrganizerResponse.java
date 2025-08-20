package com.staj.biletbul.response;

import java.util.List;

public record AllEventsOfOrganizerResponse(Long id,
                                           String organizerName,
                                           String email,
                                           List<EventResponse> events) {
}
