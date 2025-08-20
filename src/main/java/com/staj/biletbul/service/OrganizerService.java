package com.staj.biletbul.service;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.exception.OrganizerAlreadyExistsException;
import com.staj.biletbul.exception.OrganizerNotFoundException;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.OrganizerMapper;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.request.CreateOrganizerRequest;
import com.staj.biletbul.response.AllEventsOfOrganizerResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.OrganizerResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final OrganizerMapper organizerMapper;
    private final EventMapper eventMapper;

    public OrganizerService(OrganizerRepository organizerRepository,
                            OrganizerMapper organizerMapper,
                            EventMapper eventMapper) {
        this.organizerRepository = organizerRepository;
        this.organizerMapper = organizerMapper;
        this.eventMapper = eventMapper;
    }

    public List<OrganizerResponse> getAllOrganizers() {
        List<Organizer> organizers = organizerRepository.findAll();
        List<OrganizerResponse> organizerResponses = organizers.stream().map(
                organizerMapper::mapToOrganizerResponse
        ).toList();
        return organizerResponses;
    }

    public OrganizerResponse getOrganizerById(Long id) {
        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(()-> new OrganizerNotFoundException("Organizer not found with id: " + id));
        return organizerMapper.mapToOrganizerResponse(organizer);
    }

    public AllEventsOfOrganizerResponse getAllEventOfOrganizerById(Long id) {
        Organizer organizer = organizerRepository.findById(id)
                .orElseThrow(() -> new OrganizerNotFoundException("Organizer not found with id: " + id));
        List<EventResponse> eventResponses = organizer.getEventList()
                .stream()
                .map(eventMapper::mapToResponse)
                .toList();
        AllEventsOfOrganizerResponse response = new AllEventsOfOrganizerResponse(
                organizer.getId(),
                organizer.getOrganizerName(),
                organizer.getEmail(),
                eventResponses
        );
        return response;
    }

    @Transactional
    public OrganizerResponse createOrganizer(CreateOrganizerRequest request) {
        if (organizerRepository.findByEmail(request.email()).isPresent()) {
            throw new OrganizerAlreadyExistsException
                    ("Organizer with email: " + request.email() + " already exists");
        }
        if(organizerRepository.findByOrganizerName(request.organizerName()).isPresent()) {
            throw new OrganizerAlreadyExistsException
                    ("Organizer with name: " + request.organizerName() + " already exists");
        }
        Organizer createdOrganizer = organizerRepository.save
                (organizerMapper.mapToEntity(request));
        return organizerMapper.mapToOrganizerResponse(createdOrganizer);
    }

    @Transactional
    public ResourceDeletedResponse deleteOrganizer(Long id) {
        Organizer organizerToDelete = organizerRepository.findById(id)
                        .orElseThrow(()-> new OrganizerNotFoundException("Organizer not found with id: " + id));
        organizerRepository.delete(organizerToDelete);
        return new ResourceDeletedResponse("Organizer with id: " + id + " deleted successfully");
    }

}
