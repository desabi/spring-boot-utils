package com.desabi.guide.api.versioning.api_versioning_a.domain;

import lombok.*;
import java.time.LocalDateTime;

// No @Entity, no @Table, no @Column — just a clean domain object
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
}