package com.staj.biletbul.service;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


    public Event getEventById(Long id) {
        return eventRepository.getReferenceById(id);
    }

    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        Event eventToDelete = getEventById(id);
        eventRepository.delete(eventToDelete);
    }
}
