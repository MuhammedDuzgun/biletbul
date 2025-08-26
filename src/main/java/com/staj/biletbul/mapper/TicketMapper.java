package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.Ticket;
import com.staj.biletbul.response.TicketResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketResponse mapToTicketResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse(
                ticket.getUser().getFullName(),
                ticket.getPrice(),
                ticket.getEvent().getTitle(),
                ticket.getEvent().getDescription(),
                ticket.getEvent().getStartTime(),
                ticket.getEvent().getEndTime(),
                ticket.getEvent().getVenue().getName(),
                ticket.getEvent().getVenue().getAddress(),
                ticket.getEvent().getOrganizer().getOrganizerName(),
                ticket.getEvent().getArtist().getName(),
                ticket.getEvent().getCity().getName(),
                ticket.getSeat().getSeatNumber(),
                ticket.getSeat().getSeatType()
        );
        return response;
    }

}
