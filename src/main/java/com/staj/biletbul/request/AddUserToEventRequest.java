package com.staj.biletbul.request;

import com.staj.biletbul.enums.SeatType;

public record AddUserToEventRequest(String email, SeatType seatType) {
}