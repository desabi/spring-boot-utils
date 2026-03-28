package com.desabi.guide.api.versioning.header.v1.mapper;

import com.desabi.guide.api.versioning.header.domain.User;
import com.desabi.guide.api.versioning.header.v1.dto.request.CreateUserRequestV1;
import com.desabi.guide.api.versioning.header.v1.dto.response.UserResponseV1;
import org.springframework.stereotype.Component;

/**
 * Translates between the v1 API contract and the internal {@link User}
 * domain object.
 *
 * <p>This class is the only place in the codebase that knows about the
 * v1 field-naming conventions ({@code userId}, merged {@code name}).
 * If the domain model gains new fields in the future, this mapper
 * intentionally ignores them — v1 clients never see fields added after
 * the v1 contract was frozen.</p>
 */
@Component
public class UserMapperV1 {

    /**
     * Converts a {@link User} domain object into a {@link UserResponseV1}.
     *
     * <p>The {@code firstName} and {@code lastName} fields of the domain
     * object are concatenated into the single {@code name} field expected
     * by v1 consumers.</p>
     *
     * @param user the domain object to convert; must not be {@code null}
     * @return the v1-shaped response DTO
     */
    public UserResponseV1 toResponse(User user) {
        return new UserResponseV1(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                user.getEmail()
        );
    }

    /**
     * Converts a {@link CreateUserRequestV1} into a {@link User} domain object.
     *
     * <p>The single {@code name} field is split on the first space into
     * {@code firstName} and {@code lastName}. If no space is present,
     * {@code lastName} is set to an empty string.</p>
     *
     * @param request the inbound v1 request body; must not be {@code null}
     * @return a new {@link User} ready to be passed to the service layer;
     *         {@code id} and {@code createdAt} are left {@code null} and
     *         will be assigned by the repository on save
     */
    public User toDomain(CreateUserRequestV1 request) {
        String[] parts = request.name().trim().split(" ", 2);
        return User.builder()
                .firstName(parts[0])
                .lastName(parts.length > 1 ? parts[1] : "")
                .email(request.email())
                .build();
    }
}