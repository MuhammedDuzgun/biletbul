package com.staj.biletbul.response;

import java.util.List;

public record AllEventsOfEventCategoryResponse(Long id,
                                               String categoryName,
                                               String description,
                                               List<EventResponse> events) {
}
