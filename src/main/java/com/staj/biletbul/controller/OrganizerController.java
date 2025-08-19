package com.staj.biletbul.controller;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.response.OrganizerResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.service.OrganizerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<OrganizerResponse>> getAllOrganizers() {
        return ResponseEntity.ok(organizerService.getAllOrganizers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizerResponse> getOrganizerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(organizerService.getOrganizerById(id));
    }

    @PostMapping
    public ResponseEntity<OrganizerResponse> createOrganizer(@RequestBody Organizer organizer) {
        return ResponseEntity.ok(organizerService.createOrganizer(organizer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteOrganizer(@PathVariable("id") Long id) {
        return new ResponseEntity<>(organizerService.deleteOrganizer(id), HttpStatus.ACCEPTED);
    }

}