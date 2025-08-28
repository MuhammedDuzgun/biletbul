package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Ticket;
import com.staj.biletbul.enums.EventType;
import com.staj.biletbul.response.TicketResponse;
import com.staj.biletbul.response.TicketTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketResponse mapToTicketResponse(Ticket ticket) {

        TicketTypeResponse typeResponse = new TicketTypeResponse(
                ticket.getId(),
                ticket.getTicketType().getName(),
                ticket.getPrice()
        );

        TicketResponse response = new TicketResponse();
        response.setFullName(ticket.getUser().getFullName());
        response.setPrice(ticket.getPrice());
        response.setEventTitle(ticket.getEvent().getTitle());
        response.setEventDescription(ticket.getEvent().getDescription());
        response.setStartTime(ticket.getEvent().getStartTime());
        response.setEndTime(ticket.getEvent().getEndTime());
        response.setVenueName(ticket.getEvent().getVenue().getName());
        response.setVenueAddress(ticket.getEvent().getVenue().getAddress());
        response.setOrganizerName(ticket.getEvent().getOrganizer().getOrganizerName());
        response.setArtistName(ticket.getEvent().getArtist().getName());
        response.setCityName(ticket.getEvent().getCity().getName());
        if (ticket.getEvent().getEventType().equals(EventType.SEATED)) {
            response.setSeatNumber(ticket.getSeat().getSeatNumber());
        }
        response.setTicketType(typeResponse);
        return response;
    }

}
