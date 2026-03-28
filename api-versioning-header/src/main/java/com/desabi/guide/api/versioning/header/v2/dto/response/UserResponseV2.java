package com.desabi.guide.api.versioning.header.v2.dto.response;

import java.time.LocalDateTime;

/**
 * Response body returned by the v2 API contract.
 *
 * <p>Richer than v1 in several ways:</p>
 * <ul>
 *   <li>{@code id} replaces the old {@code userId} field name.</li>
 *   <li>Name is exposed as separate {@code firstName} / {@code lastName}
 *       fields instead of a single merged string.</li>
 *   <li>{@code phoneNumber} and {@code department} are new fields.</li>
 *   <li>{@code createdAt} is now included for audit purposes.</li>
 * </ul>
 *
 * @param id          the user's unique identifier
 * @param firstName   the user's given name
 * @param lastName    the user's family name
 * @param email       the user's email address
 * @param phoneNumber optional contact phone number
 * @param department  optional department or team name
 * @param createdAt   timestamp of when the record was first created
 */
public record UserResponseV2(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String department,
        LocalDateTime createdAt
) {}