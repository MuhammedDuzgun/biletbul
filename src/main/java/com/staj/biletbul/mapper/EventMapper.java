package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventResponse mapToResponse(Event event) {
        EventResponse response = new EventResponse(
                event.getId(),
                event.getDescription(),
                event.getStandardSeats(),
                event.getVipSeats(),
                event.isAllStandardSeatsReserved(),
                event.isAllVipSeatsReserved(),
                event.getStandardSeatPrice(),
                event.getVipSeatPrice(),
                event.getStartTime(),
                event.getEndTime(),
                event.getOrganizer().getOrganizerName(),
                event.getEventCategory().getCategoryName()
        );
        return response;
    }

    public Event mapToEntity(CreateEventRequest request) {
        Event event = new Event();
        event.setDescription(request.description());
        event.setStandardSeats(request.standardSeats());
        event.setVipSeats(request.vipSeats());
        event.setStandardSeatPrice(request.standardSeatPrice());
        event.setVipSeatPrice(request.vipSeatPrice());
        event.setStartTime(request.startTime());
        event.setEndTime(request.endTime());
        event.setStartTime(request.startTime());
        event.setEndTime(request.endTime());
        return event;
    }
}
