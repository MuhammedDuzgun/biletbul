package com.staj.biletbul.request;

import com.staj.biletbul.enums.EventStatus;

public record UpdateEventStatusRequest(EventStatus eventStatus) {
}
