package com.desabi.guide.api.versioning.api_versioning_a.service;

import com.desabi.guide.api.versioning.api_versioning_a.domain.User;
import com.desabi.guide.api.versioning.api_versioning_a.exception.UserNotFoundException;
import com.desabi.guide.api.versioning.api_versioning_a.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // -----------------------------------------------------------------
    // Queries
    // -----------------------------------------------------------------

    public List<User> findAll() {
        log.info("Fetching all users — store size: {}", userRepository.findAll().size());
        return userRepository.findAll();
    }

    public User findById(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // -----------------------------------------------------------------
    // Mutations
    // -----------------------------------------------------------------

    public User create(User user) {
        log.info("Creating user with email: {}", user.getEmail());

        // Guard: reject duplicate emails before hitting the store
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + user.getEmail());
        }

        // id and createdAt are assigned inside repository.save()
        return userRepository.save(user);
    }

    public User update(Long id, User updatedData) {
        log.info("Updating user with id: {}", id);

        // Fetch existing record — throws 404 if not found
        User existing = findById(id);

        // If the email is being changed, make sure the new one is not taken
        boolean emailChanged = !existing.getEmail()
                .equalsIgnoreCase(updatedData.getEmail());

        if (emailChanged && userRepository.existsByEmail(updatedData.getEmail())) {
            throw new IllegalArgumentException(
                    "Email already in use: " + updatedData.getEmail());
        }

        // Apply changes — id and createdAt are intentionally preserved
        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setPhoneNumber(updatedData.getPhoneNumber());

        return userRepository.save(existing);
    }

    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);

        // Fetch first so we throw 404 if the id does not exist,
        // rather than silently doing nothing
        User user = findById(id);
        userRepository.delete(user);
    }
}