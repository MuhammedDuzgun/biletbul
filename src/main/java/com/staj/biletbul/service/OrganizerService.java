package com.staj.biletbul.service;

import com.staj.biletbul.entity.Organizer;
import com.staj.biletbul.entity.Role;
import com.staj.biletbul.enums.RoleName;
import com.staj.biletbul.exception.OrganizerAlreadyExistsException;
import com.staj.biletbul.exception.OrganizerNotFoundException;
import com.staj.biletbul.mapper.EventMapper;
import com.staj.biletbul.mapper.OrganizerMapper;
import com.staj.biletbul.repository.OrganizerRepository;
import com.staj.biletbul.repository.RoleRepository;
import com.staj.biletbul.request.CreateOrganizerRequest;
import com.staj.biletbul.response.AllEventsOfOrganizerResponse;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.OrganizerResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrganizerService {

    private final OrganizerRepository organizerRepository;
    private final OrganizerMapper organizerMapper;
    private final EventMapper eventMapper;
    private final RoleRepository roleRepository;

    public OrganizerService(OrganizerRepository organizerRepository,
                            OrganizerMapper organizerMapper,
                            EventMapper eventMapper,
                            RoleRepository roleRepository) {
        this.organizerRepository = organizerRepository;
        this.organizerMapper = organizerMapper;
        this.eventMapper = eventMapper;
        this.roleRepository = roleRepository;
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
    public OrganizerResponse createOrganizer(CreateOrganizerRequest request) {
        if (organizerRepository.findByEmail(request.email()).isPresent()) {
            throw new OrganizerAlreadyExistsException
                    ("Organizer with email: " + request.email() + " already exists");
        }
        if(organizerRepository.findByOrganizerName(request.organizerName()).isPresent()) {
            throw new OrganizerAlreadyExistsException
                    ("Organizer with name: " + request.organizerName() + " already exists");
        }

        //rol
        Set<Role> roles = new HashSet<>();
        Role roleOrganizer = roleRepository.findByRoleName(RoleName.ROLE_ORGANIZER);
        roles.add(roleOrganizer);

        Organizer organizerToCreate = organizerMapper.mapToEntity(request);
        organizerToCreate.setRoles(roles);

        return organizerMapper.mapToOrganizerResponse(organizerRepository.save(organizerToCreate));
    }

    @Transactional
    public ResourceDeletedResponse deleteOrganizer(Long id) {
        Organizer organizerToDelete = organizerRepository.findById(id)
                        .orElseThrow(()-> new OrganizerNotFoundException("Organizer not found with id: " + id));

        //organizer'ın event'lerini de sil
        organizerToDelete.getEventList().clear();

        organizerRepository.delete(organizerToDelete);
        return new ResourceDeletedResponse("Organizer with id: " + id + " deleted successfully");
    }

}
