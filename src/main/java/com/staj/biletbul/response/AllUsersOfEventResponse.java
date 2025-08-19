package com.staj.biletbul.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AllUsersOfEventResponse(Long id,
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
                                      List<UserResponse> users) {
}
