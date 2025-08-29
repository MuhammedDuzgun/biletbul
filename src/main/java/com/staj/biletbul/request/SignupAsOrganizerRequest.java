package com.staj.biletbul.request;

public record SignupAsOrganizerRequest(String organizerName,
                                       String phoneNumber,
                                       String email,
                                       String password) {
}
