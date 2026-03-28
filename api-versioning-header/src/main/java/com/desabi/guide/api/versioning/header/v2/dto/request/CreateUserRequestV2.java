package com.desabi.guide.api.versioning.header.v2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body for creating or updating a user via the v2 API contract.
 *
 * <p>Improvements over v1:</p>
 * <ul>
 *   <li>The name is properly split into {@code firstName} and
 *       {@code lastName}, enabling better sorting and personalisation.</li>
 *   <li>{@code phoneNumber} and {@code department} are new optional fields
 *       that were not available in v1.</li>
 * </ul>
 *
 * <p>Consumers sending {@code X-API-Version: 2} must use this shape.
 * Sending a v1-shaped body (with a single {@code name} field) to a v2
 * endpoint will result in a {@code 400 Bad Request}.</p>
 *
 * @param firstName   the user's given name; required
 * @param lastName    the user's family name; required
 * @param email       the user's unique email address; required, must be valid
 * @param phoneNumber an optional contact number in any format
 * @param department  an optional department or team name
 */
public record CreateUserRequestV2(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid address")
        String email,

        String phoneNumber,

        String department
) {}