package com.staj.biletbul.request;

public record CreateOrganizerRequest(String organizerName,
                                     String email,
                                     String password) {
}
