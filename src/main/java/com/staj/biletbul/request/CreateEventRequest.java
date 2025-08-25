package com.staj.biletbul.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateEventRequest(String title,
                                 String description,
                                 Integer standardSeats,
                                 Integer vipSeats,
                                 BigDecimal standardSeatPrice,
                                 BigDecimal vipSeatPrice,
                                 LocalDateTime startTime,
                                 LocalDateTime endTime,
                                 String organizerMail,
                                 String venueName,
                                 String eventCategoryName,
                                 String artistName,
                                 String cityName) {
}
