package com.staj.biletbul.controller;

import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.response.TicketResponse;
import com.staj.biletbul.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> addUserToEvent(@RequestBody AddUserToEventRequest request) {
        return new ResponseEntity<>(bookService.addUserToEvent(request), HttpStatus.CREATED);
    }

}
