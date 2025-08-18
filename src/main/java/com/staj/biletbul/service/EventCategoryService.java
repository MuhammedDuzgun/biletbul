package com.staj.biletbul.service;

import com.staj.biletbul.entity.EventCategory;
import com.staj.biletbul.exception.EventCategoryAlreadyExistsException;
import com.staj.biletbul.exception.EventCategoryNotFoundException;
import com.staj.biletbul.repository.EventCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventCategoryService {

    private final EventCategoryRepository eventCategoryRepository;

    public EventCategoryService(EventCategoryRepository eventCategoryRepository) {
        this.eventCategoryRepository = eventCategoryRepository;
    }

    public List<EventCategory> findAll() {
        return eventCategoryRepository.findAll();
    }

    public EventCategory findById(Long id) {
        return eventCategoryRepository.findById(id)
                .orElseThrow(()-> new EventCategoryNotFoundException("Event category not found with id: " + id));
    }

    @Transactional
    public EventCategory createEventCategory(EventCategory eventCategory) {
        if (eventCategoryRepository.findByCategoryName(eventCategory.getCategoryName()).isPresent()) {
            throw new EventCategoryAlreadyExistsException("Event category already exists");
        }
        return eventCategoryRepository.save(eventCategory);
    }

    @Transactional
    public void deleteEventCategoryById(Long id) {
        EventCategory eventCategoryToDelete = eventCategoryRepository.findById(id)
                .orElseThrow(()-> new EventCategoryNotFoundException("Event category not found with id: " + id));
        eventCategoryRepository.delete(eventCategoryToDelete);
    }

}
