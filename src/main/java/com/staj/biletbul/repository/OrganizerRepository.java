package com.staj.biletbul.repository;

import com.staj.biletbul.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Optional<Organizer> findByEmail(String email);
    Optional<Organizer> findByOrganizerName(String organizerName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM organizer_roles WHERE organizer_id = :id", nativeQuery = true)
    void deleteOrganizerRoles(Long id);
}
