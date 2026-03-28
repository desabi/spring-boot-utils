package com.desabi.guide.api.versioning.api_versioning_a.v2.controller;

import com.desabi.guide.api.versioning.api_versioning_a.domain.User;
import com.desabi.guide.api.versioning.api_versioning_a.service.UserService;
import com.desabi.guide.api.versioning.api_versioning_a.v2.dto.request.CreateUserRequestV2;
import com.desabi.guide.api.versioning.api_versioning_a.v2.dto.response.UserResponseV2;
import com.desabi.guide.api.versioning.api_versioning_a.v2.mapper.UserMapperV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v2/users")     // ← URI path versioning happens HERE
@RequiredArgsConstructor
public class UserControllerV2 {

    private final UserService userService;       // shared — same service as v1
    private final UserMapperV2 userMapperV2;     // version-specific — v2 contract only

    @GetMapping
    public ResponseEntity<List<UserResponseV2>> findAll() {
        List<UserResponseV2> response = userService.findAll()
                .stream()
                .map(userMapperV2::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseV2> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(userMapperV2.toResponse(user));
    }

    @PostMapping
    public ResponseEntity<UserResponseV2> create(@Valid @RequestBody CreateUserRequestV2 request) {
        User saved = userService.create(userMapperV2.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapperV2.toResponse(saved));
    }

    @PutMapping("/{id}")   // ← v2 adds full update support (v1 didn't have it)
    public ResponseEntity<UserResponseV2> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserRequestV2 request) {
        User saved = userService.update(id, userMapperV2.toDomain(request));
        return ResponseEntity.ok(userMapperV2.toResponse(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}