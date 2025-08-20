package com.staj.biletbul.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventResponse(Long id,
                            String description,
                            Integer standardSeats,
                            Integer vipSeats,
                            boolean isAllStandardSeatsReserved,
                            boolean isAllVipSeatsReserved,
                            BigDecimal standardSeatPrice,
                            BigDecimal vipSeatPrice,
                            LocalDateTime startTime,
                            LocalDateTime endTime,
                            String organizerName,
                            String eventCategoryName,
                            String artistName) {
}
