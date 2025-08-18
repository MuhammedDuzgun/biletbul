package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.request.CreateEventRequest;
import com.staj.biletbul.response.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    private final UserMapper userMapper;

    public EventMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public EventResponse mapToResponse(Event event) {
        EventResponse response = new EventResponse(
                event.getId(),
                event.getDescription(),
                event.getStandardSeats(),
                event.getVipSeats(),
                event.isAllStandardSeatsReserved(),
                event.isAllVipSeatsReserved(),
                event.getUsers().stream().map(userMapper::mapToUserResponse).toList(),
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
        return event;
    }

}
