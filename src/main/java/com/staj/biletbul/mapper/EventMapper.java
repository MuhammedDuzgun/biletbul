package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventResponse mapToResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setStatus(event.getEventStatus());
        response.setStartTime(event.getStartTime());
        response.setEndTime(event.getEndTime());
        response.setVenueName(event.getVenue().getName());
        response.setOrganizerName(event.getOrganizer().getOrganizerName());
        response.setEventCategoryName(event.getEventCategory().getCategoryName());
        if (event.getArtist() != null) {
            response.setArtistName(event.getArtist().getName()); //todo: kontrol ?
        }
        response.setCityName(event.getCity().getName());

        return response;
    }

    public Event mapToEntity(CreateEventRequest request) {
        Event event = new Event();
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setEventType(request.eventType());
        event.setTicketTypes(request.ticketTypes());
        event.setStartTime(request.startTime());
        event.setEndTime(request.endTime());
        event.setStartTime(request.startTime());
        event.setEndTime(request.endTime());
        return event;
    }
}
