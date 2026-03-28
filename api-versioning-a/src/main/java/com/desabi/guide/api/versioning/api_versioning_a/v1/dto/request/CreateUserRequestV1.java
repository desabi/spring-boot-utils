package com.desabi.guide.api.versioning.api_versioning_a.v1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Old contract: name is a single merged field (firstName + lastName)
public record CreateUserRequestV1(

        @NotBlank(message = "Name is required")
        String name,        // ← single field, no split (this is what v2 broke)

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email
) {}