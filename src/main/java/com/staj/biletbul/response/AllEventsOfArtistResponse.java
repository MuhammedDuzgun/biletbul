package com.staj.biletbul.response;

import java.util.List;

public record AllEventsOfArtistResponse(Long id,
                                        String artistName,
                                        List<EventResponse> events) {
}
