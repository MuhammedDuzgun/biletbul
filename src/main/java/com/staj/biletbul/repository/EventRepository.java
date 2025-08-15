package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
