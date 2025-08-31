package com.staj.biletbul.mapper;

import com.staj.biletbul.entity.TicketType;
import com.staj.biletbul.response.TicketTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class TicketTypeMapper {

    public TicketTypeResponse mapToTicketTypeResponse(TicketType ticketType) {
        TicketTypeResponse response = new TicketTypeResponse(
                ticketType.getId(),
                ticketType.getName(),
                ticketType.getPrice()
        );
        return response;
    }

}
