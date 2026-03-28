package com.desabi.guide.api.versioning.api_versioning_a.v2.dto.response;

import java.time.LocalDateTime;

// New contract: richer response with split name, phoneNumber, and createdAt
public record UserResponseV2(

        Long id,               // ← renamed from v1's 'userId' (breaking change)
        String firstName,      // ← split from v1's merged 'name' (breaking change)
        String lastName,
        String email,
        String phoneNumber,    // ← new field
        LocalDateTime createdAt  // ← new field
) {}