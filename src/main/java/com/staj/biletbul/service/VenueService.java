package com.staj.biletbul.service;

import com.staj.biletbul.entity.City;
import com.staj.biletbul.entity.Venue;
import com.staj.biletbul.exception.CityNotFoundException;
import com.staj.biletbul.exception.VenueAlreadyExistsException;
import com.staj.biletbul.exception.VenueNotFoundException;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.VenueMapper;
import com.staj.biletbul.repository.CityRepository;
import com.staj.biletbul.repository.VenueRepository;
import com.staj.biletbul.request.CreateVenueRequest;
import com.staj.biletbul.response.AllEventsOfVenueResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.response.VenueResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueMapper venueMapper;
    private final CityRepository cityRepository;
    private final EventMapper eventMapper;

    public VenueService(VenueRepository venueRepository,
                        VenueMapper venueMapper,
                        CityRepository cityRepository,
                        EventMapper eventMapper) {
        this.venueRepository = venueRepository;
        this.venueMapper = venueMapper;
        this.cityRepository = cityRepository;
        this.eventMapper = eventMapper;
    }

    public List<VenueResponse> getAllVenues() {
        List<Venue> venues = venueRepository.findAll();
        return venues
                .stream()
                .map(venueMapper::mapToVenueResponse)
                .toList();
    }

    public VenueResponse getVenueById(Long id) {
        return venueMapper.mapToVenueResponse(venueRepository.findById(id)
                .orElseThrow(()-> new VenueNotFoundException("Venue not found with id : " + id)));
    }

    public AllEventsOfVenueResponse getAllEventsOfVenueById(Long id) {
        Venue venue = venueRepository.findById(id)
                .orElseThrow(()-> new VenueNotFoundException("Venue not found with id : " + id));

        List<EventResponse> events = venue.getEventList()
                .stream()
                .map(eventMapper::mapToResponse)
                .toList();

        AllEventsOfVenueResponse response = new AllEventsOfVenueResponse(
                venue.getId(),
                venue.getName(),
                venue.getPhoneNumber(),
                venue.getAddress(),
                venue.getCity().getName(),
                events
        );

        return response;
    }

    @Transactional
    public VenueResponse createVenue(CreateVenueRequest request) {
        if (venueRepository.existsByName(request.venueName())) {
            throw new VenueAlreadyExistsException("Venue already exist with venueName : " + request.venueName());
        }

        Venue venueToCreate = venueMapper.mapToVenue(request);

        //şehir kontrolü
        City city = cityRepository.findByName(request.cityName())
                .orElseThrow(()-> new CityNotFoundException("City not found with venueName : " + request.cityName()));

        venueToCreate.setCity(city);

        return venueMapper.mapToVenueResponse(venueRepository.save(venueToCreate));
    }

    @Transactional
    public ResourceDeletedResponse deleteVenueById(Long id) {
        Venue venueToDelete = venueRepository.findById(id)
                .orElseThrow(() -> new VenueNotFoundException("Venue not found with id : " + id));

        venueRepository.delete(venueToDelete);
        return new ResourceDeletedResponse("Venue with id : " + id + " has been deleted");
    }
}
