package com.staj.biletbul.response;

import java.util.List;

public record EventResponse(Long id,
                            String description,
                            Integer standardSeats,
                            Integer vipSeats,
                            boolean isAllStandardSeatsReserved,
                            boolean isAllVipSeatsReserved,
                            List<UserResponse> users,
                            String organizerName,
                            String  eventCategoryName) {
}
