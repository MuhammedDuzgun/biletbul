package com.staj.biletbul.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventResponse(Long id,
                            String title,
                            String description,
                            Integer standardSeats,
                            Integer vipSeats,
                            boolean isAllStandardSeatsReserved,
                            boolean isAllVipSeatsReserved,
                            BigDecimal standardSeatPrice,
                            BigDecimal vipSeatPrice,
                            LocalDateTime startTime,
                            LocalDateTime endTime,
                            String venueName,
                            String organizerName,
                            String eventCategoryName,
                            String artistName,
                            String cityName) {
}
