package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tickets WHERE event_id = :eventId", nativeQuery = true)
    void deleteTicketsByEventId(Long eventId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tickets WHERE user_id = :userId", nativeQuery = true)
    void deleteTicketsByUserId(Long userId);

}
