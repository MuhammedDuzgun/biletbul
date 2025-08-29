package com.staj.biletbul.request;

public record AddUserToEventRequest(String eventTitle,
                                    String seatNumber,
                                    String ticketTypeName) {
}