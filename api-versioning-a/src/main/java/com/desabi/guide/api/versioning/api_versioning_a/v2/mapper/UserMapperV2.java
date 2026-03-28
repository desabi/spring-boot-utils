package com.desabi.guide.api.versioning.api_versioning_a.v2.mapper;

import com.desabi.guide.api.versioning.api_versioning_a.domain.User;
import com.desabi.guide.api.versioning.api_versioning_a.v2.dto.request.CreateUserRequestV2;
import com.desabi.guide.api.versioning.api_versioning_a.v2.dto.response.UserResponseV2;
import org.springframework.stereotype.Component;

@Component
public class UserMapperV2 {

    // Domain → V2 response DTO
    public UserResponseV2 toResponse(User user) {
        return new UserResponseV2(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCreatedAt()
        );
    }

    // V2 request DTO → Domain
    public User toDomain(CreateUserRequestV2 request) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();
    }
}