package com.staj.biletbul.service;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.exception.OrganizerAlreadyExistsException;
import com.staj.biletbul.exception.OrganizerNotFoundException;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.OrganizerMapper;
import com.staj.biletbul.repository.EventRepository;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.repository.RoleRepository;
import com.staj.biletbul.response.AllEventsOfOrganizerResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.OrganizerResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import com.staj.biletbul.security.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final OrganizerMapper organizerMapper;
    private final EventMapper eventMapper;
    private final RoleRepository roleRepository;
    private final EventRepository eventRepository;

    public OrganizerService(OrganizerRepository organizerRepository,
                            OrganizerMapper organizerMapper,
                            EventMapper eventMapper,
                            RoleRepository roleRepository,
                            EventRepository eventRepository) {
        this.organizerRepository = organizerRepository;
        this.organizerMapper = organizerMapper;
        this.eventMapper = eventMapper;
        this.roleRepository = roleRepository;
        this.eventRepository = eventRepository;
    }

    public List<OrganizerResponse> getAllOrganizers() {
        List<OrganizerResponse> organizerResponses = organizerRepository.findAll()
                .stream()
                .map(organizerMapper::mapToOrganizerResponse)
                .toList();
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
    public ResourceDeletedResponse deleteOrganizer(Long id, CustomUserDetails userDetails) {
        //organizer getir
        Organizer organizerToDelete = organizerRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(()-> new OrganizerNotFoundException("organizer not found"));

        //id'ler uyusuyor mu
        if (! organizerToDelete.getId().equals(id)) {
            throw new OrganizerAlreadyExistsException("Silmek istedigin organizatör değilsin !");
        }

        //organizer'ın event'lerini de sil
        eventRepository.deleteEventByOrganizerId(id);

        //organizer_roles temizle
        organizerRepository.deleteOrganizerRoles(id);

        //organizer sil
        organizerRepository.delete(organizerToDelete);

        return new ResourceDeletedResponse("Organizer with id: " + id + " deleted successfully");
    }

}
