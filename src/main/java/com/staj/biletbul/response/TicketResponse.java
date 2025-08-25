package com.staj.biletbul.response;

import com.staj.biletbul.enums.SeatType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TicketResponse(String fullName,
                             BigDecimal price,
                             String eventTitle,
                             String eventDescription,
                             LocalDateTime startTime,
                             LocalDateTime endTime,
                             String venueName,
                             String venueAddress,
                             String organizerName,
                             String artistName,
                             String cityName,
                             String seatNumber,
                             SeatType seatType) {
}
