package com.staj.biletbul.controller;

import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.service.EventCategoryService;
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
    public List<EventCategory> getAllEventCategories() {
        return eventCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public EventCategory getEventCategoryById(@PathVariable("id") Long id) {
        return eventCategoryService.findById(id);
    }

    @PostMapping
    public EventCategory createEventCategory(@RequestBody EventCategory eventCategory) {
        return eventCategoryService.createEventCategory(eventCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryEventById(@PathVariable("id") Long id) {
        eventCategoryService.deleteEventCategoryById(id);
    }

}
