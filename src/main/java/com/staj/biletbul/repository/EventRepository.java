package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByTitle(String title);

    Page<Event> findAllByEndTimeAfterAndEventStatus(Pageable pageable,
                                                    LocalDateTime now,
                                                    EventStatus eventStatus);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_users WHERE event_id = :id", nativeQuery = true)
    void deleteEventUsers(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM events WHERE id = :id", nativeQuery = true)
    void deleteEventById(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM events WHERE organizer_id = :organizerId", nativeQuery = true)
    void deleteEventByOrganizerId(Long organizerId);

}
