package com.staj.biletbul.service;

import com.staj.biletbul.entity.Artist;
import com.staj.biletbul.exception.ArtistAlreadyExistsException;
import com.staj.biletbul.exception.ArtistNotFoundException;
import com.staj.biletbul.mapper.ArtistMapper;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.repository.ArtistRepository;
import com.staj.biletbul.request.CreateArtistRequest;
import com.staj.biletbul.response.AllEventsOfArtistResponse;
import com.staj.biletbul.response.ArtistResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final EventMapper eventMapper;

    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper,
                         EventMapper eventMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.eventMapper = eventMapper;
    }

    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(artistMapper::mapToArtistResponse)
                .toList();
    }

    public ArtistResponse getArtistById(Long id) {
        return artistMapper.mapToArtistResponse(artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id " + id)));
    }

    public AllEventsOfArtistResponse getAllEventsOfArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(()-> new ArtistNotFoundException("Artist not found with id " + id));

        List<EventResponse> events = artist.getEventList()
                .stream()
                .map(eventMapper::mapToResponse)
                .toList();

        AllEventsOfArtistResponse response = new AllEventsOfArtistResponse(
                artist.getId(),
                artist.getName(),
                events
        );

        return response;
    }

    @Transactional
    public ArtistResponse createArtist(CreateArtistRequest request) {
        if (artistRepository.findByName(request.name()).isPresent()) {
            throw new ArtistAlreadyExistsException("Artist already exists with name " + request.name());
        }
        return artistMapper.mapToArtistResponse(artistRepository.save(artistMapper.mapToArtist(request)));
    }

    @Transactional
    public ResourceDeletedResponse deleteArtistById(Long id) {
        Artist artistToDelete = artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id " + id));
        artistRepository.delete(artistToDelete);
        return new ResourceDeletedResponse("Deleted artist with id " + id);
    }

}
