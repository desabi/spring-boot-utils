package com.desabi.guide.api.versioning.api_versioning_a.v1.dto.response;

// Old contract: uses 'userId' (renamed to 'id' in v2) and merged 'name' field
public record UserResponseV1(
        Long userId,         // ← old field name (breaking change: v2 renamed this to 'id')
        String name,         // ← merged "firstName lastName" (breaking change: v2 split these)
        String email
) {}