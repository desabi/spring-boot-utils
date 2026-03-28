package com.desabi.guide.api.versioning.api_versioning_a.v1.mapper;

import com.desabi.guide.api.versioning.api_versioning_a.domain.User;
import com.desabi.guide.api.versioning.api_versioning_a.v1.dto.request.CreateUserRequestV1;
import com.desabi.guide.api.versioning.api_versioning_a.v1.dto.response.UserResponseV1;
import org.springframework.stereotype.Component;

@Component
public class UserMapperV1 {

    // Domain → V1 response DTO
    public UserResponseV1 toResponse(User user) {
        return new UserResponseV1(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(), // merge into single 'name'
                user.getEmail()
        );
    }

    // V1 request DTO → Domain
    public User toDomain(CreateUserRequestV1 request) {
        // Split "John Doe" into firstName="John", lastName="Doe"
        String[] parts = request.name().trim().split(" ", 2);
        return User.builder()
                .firstName(parts[0])
                .lastName(parts.length > 1 ? parts[1] : "")
                .email(request.email())
                .build();
    }
}