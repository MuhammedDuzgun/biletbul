package com.staj.biletbul.controller;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.request.AddUserToEventRequest;
import com.staj.biletbul.service.EventService;
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
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable("id") Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEventById(@PathVariable("id") Long id) {
        eventService.deleteEvent(id);
    }

    @PostMapping("/{id}")
    public Event addUserToEvent(@PathVariable("id") Long eventId,
                                @RequestBody AddUserToEventRequest request) {
        return eventService.addUserToEvent(eventId, request);
    }

}