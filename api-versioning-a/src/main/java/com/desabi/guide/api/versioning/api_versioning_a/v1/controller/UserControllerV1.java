package com.desabi.guide.api.versioning.api_versioning_a.v1.controller;

import com.desabi.guide.api.versioning.api_versioning_a.domain.User;
import com.desabi.guide.api.versioning.api_versioning_a.service.UserService;
import com.desabi.guide.api.versioning.api_versioning_a.v1.dto.request.CreateUserRequestV1;
import com.desabi.guide.api.versioning.api_versioning_a.v1.dto.response.UserResponseV1;
import com.desabi.guide.api.versioning.api_versioning_a.v1.mapper.UserMapperV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")     // ← URI path versioning happens HERE
@RequiredArgsConstructor
public class UserControllerV1 {

    private final UserService userService;       // shared — no version logic
    private final UserMapperV1 userMapperV1;     // version-specific — v1 contract only

    @GetMapping
    public ResponseEntity<List<UserResponseV1>> findAll() {
        List<UserResponseV1> response = userService.findAll()
                .stream()
                .map(userMapperV1::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseV1> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok()
                // Deprecation headers — tell clients this version is going away
                .header("Deprecation", "true")
                .header("Sunset", "Sat, 01 Jan 2026 00:00:00 GMT")
                .header("Link", "</api/v2/users/" + id + ">; rel=\"successor-version\"")
                .body(userMapperV1.toResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseV1> create(@Valid @RequestBody CreateUserRequestV1 request) {
        User saved = userService.create(userMapperV1.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Deprecation", "true")
                .body(userMapperV1.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}