package com.staj.biletbul.response;

import java.math.BigDecimal;

public record SeatResponse(Long id,
                           String seatNumber,
                           BigDecimal seatPrice,
                           String eventTitle,
                           boolean isAvailable) {
}
