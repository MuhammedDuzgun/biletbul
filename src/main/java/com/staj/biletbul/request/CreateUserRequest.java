package com.staj.biletbul.request;

public record CreateUserRequest(String fullName,
                                String email,
                                String password) {
}
