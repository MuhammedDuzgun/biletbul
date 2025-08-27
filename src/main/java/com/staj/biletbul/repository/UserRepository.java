package com.staj.biletbul.repository;

import com.staj.biletbul.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_users WHERE user_id = :id", nativeQuery = true)
    void deleteUserEvents(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE user_id = :id", nativeQuery = true)
    void deleteUserRoles(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    void deleteUserById(Long id);
}
