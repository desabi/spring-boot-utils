package com.desabi.guide.api.versioning.header.exception;

/**
 * Thrown when a requested user record cannot be found in the store.
 *
 * <p>Caught by {@link GlobalExceptionHandler}, which translates it into
 * a {@code 404 Not Found} HTTP response with a structured error body.
 * This keeps exception-to-status-code mapping out of the controllers.</p>
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with a descriptive message embedding the
     * ID that was searched for.
     *
     * @param id the user ID that could not be found
     */
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}