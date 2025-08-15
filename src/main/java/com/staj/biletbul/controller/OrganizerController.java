package com.staj.biletbul.controller;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.service.OrganizerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping
    public List<Organizer> getAllOrganizers() {
        return organizerService.getAllOrganizers();
    }

    @GetMapping("/{id}")
    public Organizer getOrganizerById(@PathVariable("id") Long id) {
        return organizerService.getOrganizerById(id);
    }

    @PostMapping
    public Organizer createOrganizer(@RequestBody Organizer organizer) {
        return organizerService.createOrganizer(organizer);
    }

    @DeleteMapping("/{id}")
    public void deleteOrganizer(@PathVariable("id") Long id){
        organizerService.deleteOrganizer(id);
    }

}