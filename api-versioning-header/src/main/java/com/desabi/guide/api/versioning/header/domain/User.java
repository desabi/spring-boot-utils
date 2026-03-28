package com.desabi.guide.api.versioning.header.domain;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Core domain object representing a user in the system.
 *
 * <p>This class is intentionally free of any versioning, persistence,
 * or serialization concerns. It is the single internal representation
 * that all API versions ultimately read from and write to via their
 * respective mappers.</p>
 *
 * <p>When the data model evolves (e.g. a new field is added), only
 * this class and the relevant mapper(s) need to change — the service
 * and repository remain untouched.</p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /** Unique identifier, assigned by the repository on insert. */
    private Long id;

    /** User's given name. */
    private String firstName;

    /** User's family name. */
    private String lastName;

    /** Unique email address. Used as a natural key for duplicate detection. */
    private String email;

    /** Optional contact phone number in E.164 format. */
    private String phoneNumber;

    /** Department or team the user belongs to. Exposed only in v2. */
    private String department;

    /** Timestamp of record creation, assigned by the repository on insert. */
    private LocalDateTime createdAt;
}