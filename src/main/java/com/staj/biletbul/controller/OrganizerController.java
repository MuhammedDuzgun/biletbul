package com.staj.biletbul.controller;

import com.staj.biletbul.response.AllEventsOfOrganizerResponse;
import com.staj.biletbul.response.OrganizerResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.security.CustomUserDetails;
import com.staj.biletbul.service.OrganizerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/{id}/events")
    public ResponseEntity<AllEventsOfOrganizerResponse> getAllEventsOfOrganizer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(organizerService.getAllEventOfOrganizerById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteOrganizer(@PathVariable("id") Long id,
                                                                   @AuthenticationPrincipal
                                                                   CustomUserDetails customUserDetails) {
        return new ResponseEntity<>(organizerService.deleteOrganizer(id, customUserDetails), HttpStatus.ACCEPTED);
    }

}