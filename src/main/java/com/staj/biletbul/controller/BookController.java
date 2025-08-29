package com.staj.biletbul.controller;

import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.response.TicketResponse;
import com.staj.biletbul.security.CustomUserDetails;
import com.staj.biletbul.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> addUserToEvent(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestBody AddUserToEventRequest request) {
        return new ResponseEntity<>(bookService.addUserToEvent(userDetails, request), HttpStatus.CREATED);
    }

}
