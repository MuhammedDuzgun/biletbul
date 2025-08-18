package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Optional<Organizer> findByEmail(String email);
    Optional<Organizer> findByOrganizerName(String organizerName);
}
