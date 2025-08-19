package com.staj.biletbul.controller;

import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.request.CreateEventCategoryRequest;
import com.staj.biletbul.response.EventCategoryResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.service.EventCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-categories")
public class EventCategoryController {

    private final EventCategoryService eventCategoryService;

    public EventCategoryController(EventCategoryService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<EventCategoryResponse>> getAllEventCategories() {
        return ResponseEntity.ok(eventCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventCategoryResponse> getEventCategoryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(eventCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EventCategoryResponse> createEventCategory
            (@RequestBody CreateEventCategoryRequest request) {
        return ResponseEntity.ok(eventCategoryService.createEventCategory(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteCategoryEventById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(eventCategoryService.deleteEventCategoryById(id), HttpStatus.ACCEPTED);
    }

}
