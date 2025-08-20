package com.staj.biletbul.controller;

import com.staj.biletbul.request.CreateArtistRequest;
import com.staj.biletbul.response.ArtistResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.service.ArtistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> getArtistById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@RequestBody CreateArtistRequest request) {
        return new ResponseEntity<>(artistService.createArtist(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResourceDeletedResponse> deleteArtistById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(artistService.deleteArtistById(id), HttpStatus.ACCEPTED);
    }

}
