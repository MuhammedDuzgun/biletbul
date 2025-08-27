package com.staj.biletbul.response;

import java.time.LocalDateTime;
import java.util.List;

public record AllUsersOfEventResponse(Long id,
                                      String title,
                                      String description,
                                      LocalDateTime startTime,
                                      LocalDateTime endTime,
                                      String organizerName,
                                      String eventCategoryName,
                                      List<UserResponse> users) {
}
