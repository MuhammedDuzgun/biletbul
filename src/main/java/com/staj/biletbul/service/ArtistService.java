package com.staj.biletbul.service;

import com.staj.biletbul.exception.ArtistAlreadyExistsException;
import com.staj.biletbul.exception.ArtistNotFoundException;
import com.staj.biletbul.mapper.ArtistMapper;
import com.staj.biletbul.repository.ArtistRepository;
import com.staj.biletbul.request.CreateArtistRequest;
import com.staj.biletbul.response.ArtistResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
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

    @Transactional
    public ArtistResponse createArtist(CreateArtistRequest request) {
        if (artistRepository.findByName(request.name()).isPresent()) {
            throw new ArtistAlreadyExistsException("Artist already exists with name " + request.name());
        }
        return artistMapper.mapToArtistResponse(artistRepository.save(artistMapper.mapToArtist(request)));
    }

    @Transactional
    public ResourceDeletedResponse deleteArtistById(Long id) {
        artistRepository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException("Artist not found with id " + id));
        artistRepository.deleteById(id);
        return new ResourceDeletedResponse("Deleted artist with id " + id);
    }

}
