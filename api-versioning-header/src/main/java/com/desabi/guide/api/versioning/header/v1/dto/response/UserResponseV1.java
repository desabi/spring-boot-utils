package com.desabi.guide.api.versioning.header.v1.dto.response;

/**
 * Response body returned by the v1 API contract.
 *
 * <p>Breaking changes introduced in v2 that make this shape incompatible:</p>
 * <ul>
 *   <li>{@code userId} was renamed to {@code id}.</li>
 *   <li>{@code name} (merged) was split into {@code firstName}
 *       and {@code lastName}.</li>
 *   <li>{@code phoneNumber}, {@code department}, and {@code createdAt}
 *       are absent — they are only available in v2.</li>
 * </ul>
 *
 * @param userId the user's unique identifier (field name differs from v2)
 * @param name   the user's full name as a single merged string
 * @param email  the user's email address
 */
public record UserResponseV1(
        Long userId,
        String name,
        String email
) {}