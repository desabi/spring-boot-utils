package com.desabi.guide.api.versioning.header.v2.mapper;

import com.desabi.guide.api.versioning.header.domain.User;
import com.desabi.guide.api.versioning.header.v2.dto.request.CreateUserRequestV2;
import com.desabi.guide.api.versioning.header.v2.dto.response.UserResponseV2;
import org.springframework.stereotype.Component;

/**
 * Translates between the v2 API contract and the internal {@link User}
 * domain object.
 *
 * <p>Unlike {@code UserMapperV1}, this mapper performs a near 1-to-1
 * mapping because the v2 contract was designed to closely mirror the
 * domain model. Future versions that introduce further structural changes
 * would follow the same pattern: a dedicated mapper per version, with the
 * domain object as the shared intermediary.</p>
 */
@Component
public class UserMapperV2 {

    /**
     * Converts a {@link User} domain object into a {@link UserResponseV2}.
     *
     * <p>All available domain fields are exposed, including those that
     * are hidden from v1 consumers ({@code phoneNumber}, {@code department},
     * {@code createdAt}).</p>
     *
     * @param user the domain object to convert; must not be {@code null}
     * @return the v2-shaped response DTO
     */
    public UserResponseV2 toResponse(User user) {
        return new UserResponseV2(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDepartment(),
                user.getCreatedAt()
        );
    }

    /**
     * Converts a {@link CreateUserRequestV2} into a {@link User} domain object.
     *
     * <p>The {@code id} and {@code createdAt} fields are intentionally
     * left {@code null} — the repository assigns them automatically when
     * {@code save()} is called for the first time on a new record.</p>
     *
     * @param request the inbound v2 request body; must not be {@code null}
     * @return a new {@link User} ready to be passed to the service layer
     */
    public User toDomain(CreateUserRequestV2 request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .department(request.department())
                .build();
    }
}