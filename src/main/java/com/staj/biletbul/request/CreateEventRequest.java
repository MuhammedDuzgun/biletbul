package com.staj.biletbul.request;

import java.time.LocalDateTime;

public record CreateEventRequest(String description,
                                 Integer standardSeats,
                                 Integer vipSeats,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime,
                                 String organizerMail,
                                 String eventCategoryName) {
}
