package com.staj.biletbul.request;

import com.staj.biletbul.entity.TicketType;

public record AddUserToEventRequest(String eventTitle,
                                    String email,
                                    String seatNumber,
                                    TicketType ticketType) {
}