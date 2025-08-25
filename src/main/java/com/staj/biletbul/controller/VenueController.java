package com.staj.biletbul.controller;

import com.staj.biletbul.request.CreateVenueRequest;
import com.staj.biletbul.response.AllEventsOfVenueResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.VenueResponse;
import com.staj.biletbul.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>> getAllVenues() {
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenueById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(venueService.getVenueById(id));
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<AllEventsOfVenueResponse> getAllEventsOfVenue(@PathVariable("id") Long id) {
        return ResponseEntity.ok(venueService.getAllEventsOfVenueById(id));
    }

    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(@RequestBody CreateVenueRequest request) {
        return new ResponseEntity<>(venueService.createVenue(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteVenueById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(venueService.deleteVenueById(id), HttpStatus.ACCEPTED);
    }
}
