package com.desabi.guide.api.versioning.header.service;

import com.desabi.guide.api.versioning.header.domain.User;
import com.desabi.guide.api.versioning.header.exception.UserNotFoundException;
import com.desabi.guide.api.versioning.header.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic layer for user management.
 *
 * <p>This service is completely version-agnostic. It operates exclusively
 * on {@link User} domain objects and delegates persistence to
 * {@link UserRepository}. Controllers pass domain objects in and receive
 * domain objects back; their version-specific mappers handle all
 * translation before and after calling these methods.</p>
 *
 * <p>Business rules enforced here:</p>
 * <ul>
 *   <li>Email addresses must be unique across the entire store.</li>
 *   <li>A user must exist before it can be updated or deleted
 *       ({@code 404} is thrown otherwise).</li>
 *   <li>{@code id} and {@code createdAt} are immutable after creation.</li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // -------------------------------------------------------------------------
    // Queries
    // -------------------------------------------------------------------------

    /**
     * Retrieves every user currently held in the store.
     *
     * @return a list of all users; never {@code null}, may be empty
     */
    public List<User> findAll() {
        log.info("Fetching all users — store size: {}", userRepository.findAll().size());
        return userRepository.findAll();
    }

    /**
     * Retrieves a single user by their unique identifier.
     *
     * @param id the user ID to look up
     * @return the matching {@link User}
     * @throws UserNotFoundException if no record exists for the given ID
     */
    public User findById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // -------------------------------------------------------------------------
    // Mutations
    // -------------------------------------------------------------------------

    /**
     * Creates and stores a new user.
     *
     * <p>The {@code id} and {@code createdAt} fields of the supplied object
     * are ignored — the repository assigns them automatically on insert.</p>
     *
     * @param user the user to create; must not be {@code null}
     * @return the persisted user with its generated {@code id} and
     *         {@code createdAt} populated
     * @throws IllegalArgumentException if the email address is already in use
     */
    public User create(User user) {
        log.info("Creating user with email: {}", user.getEmail());

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + user.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Updates the mutable fields of an existing user.
     *
     * <p>Only {@code firstName}, {@code lastName}, {@code email},
     * {@code phoneNumber}, and {@code department} are updated.
     * The {@code id} and {@code createdAt} fields are always preserved.</p>
     *
     * @param id          the ID of the user to update
     * @param updatedData a {@link User} carrying the new field values
     * @return the updated user as stored
     * @throws UserNotFoundException    if no record exists for {@code id}
     * @throws IllegalArgumentException if the new email is already taken
     *                                  by a different record
     */
    public User update(Long id, User updatedData) {
        log.info("Updating user with id: {}", id);

        User existing = findById(id);

        boolean emailChanged = !existing.getEmail()
                .equalsIgnoreCase(updatedData.getEmail());

        if (emailChanged && userRepository.existsByEmail(updatedData.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + updatedData.getEmail());
        }

        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setPhoneNumber(updatedData.getPhoneNumber());
        existing.setDepartment(updatedData.getDepartment());

        return userRepository.save(existing);
    }

    /**
     * Deletes an existing user by their ID.
     *
     * <p>The record is fetched before deletion so that a missing ID
     * surfaces a meaningful {@code 404} rather than silently succeeding.</p>
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if no record exists for the given ID
     */
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = findById(id);
        userRepository.delete(user);
    }
}