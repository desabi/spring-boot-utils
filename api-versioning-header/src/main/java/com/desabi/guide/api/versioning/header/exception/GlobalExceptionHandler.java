package com.desabi.guide.api.versioning.header.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralised exception handler for all controllers in this application.
 *
 * <p>Using {@link RestControllerAdvice} means that every {@code @RestController}
 * — regardless of API version — is covered by a single set of error-handling
 * rules. Adding a new version never requires duplicating error-handling logic.</p>
 *
 * <p>All error responses share the same envelope structure:</p>
 * <pre>
 * {
 *   "timestamp": "2024-01-15T09:30:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "User not found with id: 99"
 * }
 * </pre>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the case where a user ID cannot be resolved in the store.
     * Returns {@code 404 Not Found}.
     *
     * @param ex the exception carrying the missing ID
     * @return a structured error response
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    /**
     * Handles {@code @Valid} constraint violations on request bodies.
     * Returns {@code 400 Bad Request} with a per-field breakdown.
     *
     * @param ex the exception containing field-level validation failures
     * @return a structured error response with a {@code fieldErrors} map
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fieldErrors.put(fe.getField(), fe.getDefaultMessage()));

        Map<String, Object> body = buildErrorBody(HttpStatus.BAD_REQUEST, "Validation failed");
        body.put("fieldErrors", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    /**
     * Handles duplicate email attempts on create or update.
     * Returns {@code 409 Conflict}.
     *
     * @param ex the exception carrying the conflict message
     * @return a structured error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(IllegalArgumentException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    /**
     * Fallback handler for any unexpected exception not covered above.
     * Returns {@code 500 Internal Server Error}.
     *
     * @param ex the uncaught exception
     * @return a generic structured error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    // -------------------------------------------------------------------------
    // Private helpers
    // -------------------------------------------------------------------------

    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(buildErrorBody(status, message));
    }

    private Map<String, Object> buildErrorBody(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return body;
    }
}