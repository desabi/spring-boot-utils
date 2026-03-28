package com.desabi.guide.api.versioning.api_versioning_a.repository;

import com.desabi.guide.api.versioning.api_versioning_a.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    // Auto-increment ID counter — starts after our seeded records
    private final AtomicLong idSequence = new AtomicLong(4);

    // In-memory store — simulates a database table
    private final Map<Long, User> store = new HashMap<>(Map.of(

        1L, User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.johnson@example.com")
                .phoneNumber("+1-555-0101")
                .createdAt(LocalDateTime.of(2024, 1, 15, 9, 30))
                .build(),

        2L, User.builder()
                .id(2L)
                .firstName("Bob")
                .lastName("Martinez")
                .email("bob.martinez@example.com")
                .phoneNumber("+1-555-0102")
                .createdAt(LocalDateTime.of(2024, 3, 22, 14, 0))
                .build(),

        3L, User.builder()
                .id(3L)
                .firstName("Carol")
                .lastName("White")
                .email("carol.white@example.com")
                .phoneNumber("+1-555-0103")
                .createdAt(LocalDateTime.of(2024, 6, 5, 11, 45))
                .build()
    ));

    // -----------------------------------------------------------------
    // Query methods
    // -----------------------------------------------------------------

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    // -----------------------------------------------------------------
    // Mutation methods
    // -----------------------------------------------------------------

    public User save(User user) {
        // INSERT — assign a new ID if none present
        if (user.getId() == null) {
            user.setId(idSequence.getAndIncrement());
            user.setCreatedAt(LocalDateTime.now());
        }
        // INSERT or UPDATE — put into the map either way
        store.put(user.getId(), user);
        return user;
    }

    public void delete(User user) {
        store.remove(user.getId());
    }
}