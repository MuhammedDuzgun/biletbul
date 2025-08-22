package com.staj.biletbul.response;

import com.staj.biletbul.enums.SeatType;

import java.math.BigDecimal;

public record SeatResponse(Long id,
                           String seatNumber,
                           BigDecimal seatPrice,
                           SeatType seatType,
                           String eventTitle,
                           boolean isAvailable) {
}
