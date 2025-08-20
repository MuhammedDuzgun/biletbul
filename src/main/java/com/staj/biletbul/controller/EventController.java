package com.staj.biletbul.controller;

import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.AllUsersOfEventResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<AllUsersOfEventResponse> getAllUsersOfEvent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(eventService.getAllUserOfEvent(id));
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody CreateEventRequest request) {
        return new ResponseEntity<>(eventService.createEvent(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteEventById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(eventService.deleteEvent(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<EventResponse> addUserToEvent(@PathVariable("id") Long eventId,
                                                        @RequestBody AddUserToEventRequest request) {
        return new ResponseEntity<>(eventService.addUserToEvent(eventId, request), HttpStatus.CREATED);
    }

}