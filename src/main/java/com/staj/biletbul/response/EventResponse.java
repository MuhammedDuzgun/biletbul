package com.staj.biletbul.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
                            List<UserResponse> users,
                            String organizerName,
                            String  eventCategoryName) {
}
