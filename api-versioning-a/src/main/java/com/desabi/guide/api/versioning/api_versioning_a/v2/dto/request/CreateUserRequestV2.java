package com.desabi.guide.api.versioning.api_versioning_a.v2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// New contract: name is split into separate fields + phoneNumber added
public record CreateUserRequestV2(

        @NotBlank(message = "First name is required")
        String firstName,   // ← split from v1's single 'name' (breaking change)

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        String phoneNumber  // ← new optional field (would break v1 clients if we'd added it there)
) {}