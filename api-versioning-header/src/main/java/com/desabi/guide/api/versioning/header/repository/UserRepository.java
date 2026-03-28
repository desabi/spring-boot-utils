package com.desabi.guide.api.versioning.header.repository;

import com.desabi.guide.api.versioning.header.domain.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory implementation of the user data store.
 *
 * <p>Simulates a database table using a {@link HashMap} as the backing
 * store. Ten hardcoded records are seeded at startup to support
 * development and demonstration without requiring a real database.</p>
 *
 * <p>All operations are synchronous and not thread-safe. For production
 * use, replace this class with a Spring Data JPA repository or equivalent
 * persistence mechanism without changing any other layer.</p>
 */
@Repository
public class UserRepository {

    /**
     * Auto-increment sequence. Starts at 11 so generated IDs never
     * collide with the ten pre-seeded records (ids 1–10).
     */
    private final AtomicLong idSequence = new AtomicLong(11);

    /**
     * The backing store. Keys are user IDs; values are {@link User} objects.
     * Initialised with ten hardcoded records representing diverse profiles.
     */
    private final Map<Long, User> store = new HashMap<>(Map.ofEntries(

        Map.entry(1L, User.builder()
                .id(1L)
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.johnson@desabi.com")
                .phoneNumber("+1-555-0101")
                .department("Engineering")
                .createdAt(LocalDateTime.of(2023, 1, 10, 9, 0))
                .build()),

        Map.entry(2L, User.builder()
                .id(2L)
                .firstName("Bob")
                .lastName("Martinez")
                .email("bob.martinez@desabi.com")
                .phoneNumber("+1-555-0102")
                .department("Product")
                .createdAt(LocalDateTime.of(2023, 2, 14, 10, 30))
                .build()),

        Map.entry(3L, User.builder()
                .id(3L)
                .firstName("Carol")
                .lastName("White")
                .email("carol.white@desabi.com")
                .phoneNumber("+1-555-0103")
                .department("Design")
                .createdAt(LocalDateTime.of(2023, 3, 22, 8, 15))
                .build()),

        Map.entry(4L, User.builder()
                .id(4L)
                .firstName("David")
                .lastName("Brown")
                .email("david.brown@desabi.com")
                .phoneNumber("+1-555-0104")
                .department("Engineering")
                .createdAt(LocalDateTime.of(2023, 4, 5, 14, 45))
                .build()),

        Map.entry(5L, User.builder()
                .id(5L)
                .firstName("Eva")
                .lastName("Garcia")
                .email("eva.garcia@desabi.com")
                .phoneNumber("+1-555-0105")
                .department("Marketing")
                .createdAt(LocalDateTime.of(2023, 5, 18, 11, 0))
                .build()),

        Map.entry(6L, User.builder()
                .id(6L)
                .firstName("Frank")
                .lastName("Lee")
                .email("frank.lee@desabi.com")
                .phoneNumber("+1-555-0106")
                .department("Finance")
                .createdAt(LocalDateTime.of(2023, 6, 30, 16, 20))
                .build()),

        Map.entry(7L, User.builder()
                .id(7L)
                .firstName("Grace")
                .lastName("Kim")
                .email("grace.kim@desabi.com")
                .phoneNumber("+1-555-0107")
                .department("HR")
                .createdAt(LocalDateTime.of(2023, 7, 12, 9, 50))
                .build()),

        Map.entry(8L, User.builder()
                .id(8L)
                .firstName("Henry")
                .lastName("Nguyen")
                .email("henry.nguyen@desabi.com")
                .phoneNumber("+1-555-0108")
                .department("Engineering")
                .createdAt(LocalDateTime.of(2023, 8, 25, 13, 10))
                .build()),

        Map.entry(9L, User.builder()
                .id(9L)
                .firstName("Isla")
                .lastName("Patel")
                .email("isla.patel@desabi.com")
                .phoneNumber("+1-555-0109")
                .department("Legal")
                .createdAt(LocalDateTime.of(2023, 9, 7, 15, 30))
                .build()),

        Map.entry(10L, User.builder()
                .id(10L)
                .firstName("James")
                .lastName("Wilson")
                .email("james.wilson@desabi.com")
                .phoneNumber("+1-555-0110")
                .department("Operations")
                .createdAt(LocalDateTime.of(2023, 10, 19, 10, 0))
                .build())
    ));

    // -------------------------------------------------------------------------
    // Query methods
    // -------------------------------------------------------------------------

    /**
     * Returns all users currently held in the store.
     *
     * @return a new {@link List} containing every user; never {@code null},
     *         never the backing collection itself
     */
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * Looks up a user by their unique identifier.
     *
     * @param id the user ID to search for
     * @return an {@link Optional} containing the user if found,
     *         or {@link Optional#empty()} if no record exists for that ID
     */
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Looks up a user by their email address (case-insensitive).
     *
     * @param email the email to search for; must not be {@code null}
     * @return an {@link Optional} containing the matching user, or empty
     */
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    /**
     * Checks whether any record in the store holds the given email address.
     *
     * @param email the email to check; must not be {@code null}
     * @return {@code true} if at least one record uses that email
     */
    public boolean existsByEmail(String email) {
        return store.values().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    // -------------------------------------------------------------------------
    // Mutation methods
    // -------------------------------------------------------------------------

    /**
     * Persists a user to the store.
     *
     * <p>Behaves as an upsert:</p>
     * <ul>
     *   <li>If {@code user.getId()} is {@code null}, a new ID is assigned from
     *       the sequence and {@code createdAt} is stamped with the current time
     *       (INSERT behavior).</li>
     *   <li>If an ID is already present, the existing record is overwritten
     *       (UPDATE behavior).</li>
     * </ul>
     *
     * @param user the user to save; must not be {@code null}
     * @return the saved user with its ID and {@code createdAt} populated
     */
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idSequence.getAndIncrement());
            user.setCreatedAt(LocalDateTime.now());
        }
        store.put(user.getId(), user);
        return user;
    }

    /**
     * Removes the given user from the store.
     *
     * <p>If the user's ID is not present in the store this method is a
     * no-op. Callers should verify existence before calling this method
     * to surface meaningful errors to the client.</p>
     *
     * @param user the user to remove; must not be {@code null}
     */
    public void delete(User user) {
        store.remove(user.getId());
    }
}