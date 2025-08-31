package com.staj.biletbul.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.staj.biletbul.entity.TicketType;
import com.staj.biletbul.enums.EventType;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventRequest(String title,
                                 String description,
                                 List<TicketType> ticketTypes,
                                 EventType eventType,

                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                 LocalDateTime startTime,

                                 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                 LocalDateTime endTime,

                                 String venueName,
                                 String eventCategoryName,
                                 String artistName,
                                 String cityName) {
}
