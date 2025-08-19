package com.staj.biletbul.service;

import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.exception.EventCategoryAlreadyExistsException;
import com.staj.biletbul.exception.EventCategoryNotFoundException;
import com.staj.biletbul.mapper.EventCategoryMapper;
import com.staj.biletbul.repository.EventCategoryRepository;
import com.staj.biletbul.request.CreateEventCategoryRequest;
import com.staj.biletbul.response.EventCategoryResponse;
import com.staj.biletbul.response.ResourceDeletedResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventCategoryService {

    private final EventCategoryRepository eventCategoryRepository;
    private final EventCategoryMapper eventCategoryMapper;

    public EventCategoryService(EventCategoryRepository eventCategoryRepository,
                                EventCategoryMapper eventCategoryMapper) {
        this.eventCategoryRepository = eventCategoryRepository;
        this.eventCategoryMapper = eventCategoryMapper;
    }

    public List<EventCategoryResponse> findAll() {
        List<EventCategory> eventCategories = eventCategoryRepository.findAll();
        List<EventCategoryResponse> eventCategoryResponses =
                eventCategories.stream().map(
                        eventCategoryMapper::mapToResponse
                ).toList();
        return eventCategoryResponses;
    }

    public EventCategoryResponse findById(Long id) {
        EventCategory eventCategory = eventCategoryRepository.findById(id)
                .orElseThrow(() -> new EventCategoryNotFoundException("Event category not found with id: " + id));
        return eventCategoryMapper.mapToResponse(eventCategory);
    }

    @Transactional
    public EventCategoryResponse createEventCategory(CreateEventCategoryRequest request) {
        if (eventCategoryRepository.findByCategoryName(request.categoryName()).isPresent()) {
            throw new EventCategoryAlreadyExistsException("Event category already exists");
        }
        return eventCategoryMapper.mapToResponse(eventCategoryRepository.save
                (eventCategoryMapper.mapToEntity(request)));
    }

    @Transactional
    public ResourceDeletedResponse deleteEventCategoryById(Long id) {
        EventCategory eventCategoryToDelete = eventCategoryRepository.findById(id)
                .orElseThrow(()-> new EventCategoryNotFoundException("Event category not found with id: " + id));
        eventCategoryRepository.delete(eventCategoryToDelete);
        return new ResourceDeletedResponse("Event Category with id: " + id + " deleted successfully");
    }

}
