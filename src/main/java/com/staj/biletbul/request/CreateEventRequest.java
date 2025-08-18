package com.staj.biletbul.request;

public record CreateEventRequest(String description,
                                 Integer standardSeats,
                                 Integer vipSeats,
                                 String organizerMail,
                                 String eventCategoryName) {
}
