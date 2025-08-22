package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Event;
import com.staj.biletbul.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByEventAndSeatNumber(Event event, String seatNumber);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM seats WHERE event_id = :eventId", nativeQuery = true)
    void deleteSeatsByEventId(Long eventId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM seats WHERE user_id = :userId", nativeQuery = true)
    void deleteSeatsByUserId(Long userId);

}
