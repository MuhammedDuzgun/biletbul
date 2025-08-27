package com.staj.biletbul.response;

import com.staj.biletbul.enums.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(Long id,
                            String title,
                            String description,
                            EventStatus status,
                            LocalDateTime startTime,
                            LocalDateTime endTime,
                            String venueName,
                            String organizerName,
                            String eventCategoryName,
                            String artistName,
                            String cityName) {
}
