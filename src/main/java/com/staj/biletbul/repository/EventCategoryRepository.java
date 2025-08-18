package com.staj.biletbul.repository;

import com.staj.biletbul.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {
    Optional<EventCategory> findByCategoryName(String categoryName);
}
