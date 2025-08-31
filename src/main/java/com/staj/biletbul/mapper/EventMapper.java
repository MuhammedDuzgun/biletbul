package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.EventResponse;
import com.staj.biletbul.response.TicketTypeResponse;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EventMapper {

    private final TicketTypeMapper ticketTypeMapper;

    public EventMapper(TicketTypeMapper ticketTypeMapper) {
        this.ticketTypeMapper = ticketTypeMapper;
    }

    public EventResponse mapToResponse(Event event) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setStatus(event.getEventStatus());
        response.setEventType(event.getEventType());
        response.setStartTime(event.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        response.setEndTime(event.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        response.setVenueName(event.getVenue().getName());
        response.setOrganizerName(event.getOrganizer().getOrganizerName());
        response.setEventCategoryName(event.getEventCategory().getCategoryName());
        if (event.getArtist() != null) {
            response.setArtistName(event.getArtist().getName());
        }
        response.setCityName(event.getCity().getName());

        List<TicketTypeResponse> ticketTypes = event.getTicketTypes()
                .stream()
                .map(ticketTypeMapper::mapToTicketTypeResponse)
                .toList();
        response.setTicketTypes(ticketTypes);

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
        return event;
    }
}
