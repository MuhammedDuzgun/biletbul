package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

    Optional<TicketType> findTicketTypeByEventAndName(Event event, String ticketTypeName);

}
