package com.staj.biletbul.request;

import com.staj.biletbul.entity.TicketType;
import com.staj.biletbul.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventRequest(String title,
                                 String description,
                                 List<TicketType> ticketTypes,
                                 EventType eventType,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime,
                                 String venueName,
                                 String eventCategoryName,
                                 String artistName,
                                 String cityName) {
}
