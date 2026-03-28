package com.desabi.guide.api.versioning.header.v1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body for creating a user via the v1 API contract.
 *
 * <p>In v1, the user's full name is represented as a single merged
 * {@code name} field (e.g. {@code "Alice Johnson"}). This was identified
 * as a design limitation and corrected in v2, where the name is split
 * into {@code firstName} and {@code lastName}.</p>
 *
 * <p>Consumers sending {@code X-API-Version: 1} must use this shape.
 * Sending a v2-shaped body to a v1 endpoint will result in a
 * {@code 400 Bad Request} from the {@code @Valid} constraint check.</p>
 *
 * @param name  the user's full name as a single string; required
 * @param email the user's unique email address; required, must be valid
 */
public record CreateUserRequestV1(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid address")
        String email
) {}