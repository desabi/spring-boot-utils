package com.desabi.guide.api.versioning.header.controller;

import com.desabi.guide.api.versioning.header.config.ApiV1;
import com.desabi.guide.api.versioning.header.config.ApiV2;
import com.desabi.guide.api.versioning.header.domain.User;
import com.desabi.guide.api.versioning.header.service.UserService;
import com.desabi.guide.api.versioning.header.v1.dto.request.CreateUserRequestV1;
import com.desabi.guide.api.versioning.header.v1.dto.response.UserResponseV1;
import com.desabi.guide.api.versioning.header.v1.mapper.UserMapperV1;
import com.desabi.guide.api.versioning.header.v2.dto.request.CreateUserRequestV2;
import com.desabi.guide.api.versioning.header.v2.dto.response.UserResponseV2;
import com.desabi.guide.api.versioning.header.v2.mapper.UserMapperV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for user management using header-based API versioning.
 *
 * <p>All endpoints live under the same base path ({@code /api/users}).
 * Spring selects which method to invoke based on the value of the
 * {@code X-API-Version} request header:</p>
 *
 * <ul>
 *   <li>{@code X-API-Version: 1} → routes to v1 methods (deprecated).</li>
 *   <li>{@code X-API-Version: 2} → routes to v2 methods (current).</li>
 * </ul>
 *
 * <p>If the header is missing or holds an unrecognized value, Spring
 * cannot match any handler method and returns {@code 404 Not Found}.
 * Clients should always include the header explicitly.</p>
 *
 * <p>Both versions share the same {@link UserService} — the version
 * boundary exists only in the DTOs and mappers. The URL stays clean
 * at the cost of reduced discoverability compared to URI versioning.</p>
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService   userService;
  private final UserMapperV1  userMapperV1;
  private final UserMapperV2  userMapperV2;

  // =========================================================================
  // V1 endpoints  (X-API-Version: 1)
  // =========================================================================

  /**
   * Returns all users using the v1 response contract.
   *
   * <p>Requires header {@code X-API-Version: 1}.</p>
   * <p><b>Deprecated:</b> use the v2 equivalent instead.</p>
   *
   * @return {@code 200 OK} with a list of {@link UserResponseV1} objects
   */
  @ApiV1
  @GetMapping(headers = "X-API-Version=1")
  public ResponseEntity<List<UserResponseV1>> findAllV1() {
    log.info("[v1] GET /api/users");
    List<UserResponseV1> body = userService.findAll()
        .stream()
        .map(userMapperV1::toResponse)
        .toList();
    return ResponseEntity.ok()
        .header("Deprecation", "true")
        .header("Sunset", "Sat, 01 Jan 2026 00:00:00 GMT")
        .body(body);
  }

  /**
   * Returns a single user by ID using the v1 response contract.
   *
   * <p>Requires header {@code X-API-Version: 1}.</p>
   * <p><b>Deprecated:</b> use the v2 equivalent instead.</p>
   *
   * @param id the user ID to fetch
   * @return {@code 200 OK} with the matching {@link UserResponseV1},
   *         or {@code 404} if the ID does not exist
   */
  @ApiV1
  @GetMapping(value = "/{id}", headers = "X-API-Version=1")
  public ResponseEntity<UserResponseV1> findByIdV1(@PathVariable Long id) {
    log.info("[v1] GET /api/users/{}", id);
    User user = userService.findById(id);
    return ResponseEntity.ok()
        .header("Deprecation", "true")
        .header("Sunset", "Sat, 01 Jan 2026 00:00:00 GMT")
        .header("Link", "</api/users/" + id + ">; rel=\"successor-version\"")
        .body(userMapperV1.toResponse(user));
  }

  /**
   * Creates a new user using the v1 request contract.
   *
   * <p>Requires header {@code X-API-Version: 1}.</p>
   * <p>The request body must use the v1 shape: a single {@code name}
   * field instead of separate {@code firstName} / {@code lastName}.</p>
   * <p><b>Deprecated:</b> use the v2 equivalent instead.</p>
   *
   * @param request the v1 create request body
   * @return {@code 201 Created} with the persisted {@link UserResponseV1},
   *         or {@code 400} if validation fails,
   *         or {@code 409} if the email is already in use
   */
  @ApiV1
  @PostMapping(headers = "X-API-Version=1")
  public ResponseEntity<UserResponseV1> createV1(
      @Valid @RequestBody CreateUserRequestV1 request) {
    log.info("[v1] POST /api/users — email: {}", request.email());
    User saved = userService.create(userMapperV1.toDomain(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .header("Deprecation", "true")
        .body(userMapperV1.toResponse(saved));
  }

  /**
   * Deletes a user by ID. Shared behavior across both versions.
   *
   * <p>Requires header {@code X-API-Version: 1}.</p>
   * <p><b>Deprecated:</b> use the v2 equivalent instead.</p>
   *
   * @param id the user ID to delete
   * @return {@code 204 No Content} on success,
   *         or {@code 404} if the ID does not exist
   */
  @ApiV1
  @DeleteMapping(value = "/{id}", headers = "X-API-Version=1")
  public ResponseEntity<Void> deleteV1(@PathVariable Long id) {
    log.info("[v1] DELETE /api/users/{}", id);
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }

  // =========================================================================
  // V2 endpoints  (X-API-Version: 2)
  // =========================================================================

  /**
   * Returns all users using the v2 response contract.
   *
   * <p>Requires header {@code X-API-Version: 2}.</p>
   * <p>The response includes {@code firstName}, {@code lastName},
   * {@code phoneNumber}, {@code department}, and {@code createdAt}
   * — fields not available in v1.</p>
   *
   * @return {@code 200 OK} with a list of {@link UserResponseV2} objects
   */
  @ApiV2
  @GetMapping(headers = "X-API-Version=2")
  public ResponseEntity<List<UserResponseV2>> findAllV2() {
    log.info("[v2] GET /api/users");
    List<UserResponseV2> body = userService.findAll()
        .stream()
        .map(userMapperV2::toResponse)
        .toList();
    return ResponseEntity.ok(body);
  }

  /**
   * Returns a single user by ID using the v2 response contract.
   *
   * <p>Requires header {@code X-API-Version: 2}.</p>
   *
   * @param id the user ID to fetch
   * @return {@code 200 OK} with the matching {@link UserResponseV2},
   *         or {@code 404} if the ID does not exist
   */
  @ApiV2
  @GetMapping(value = "/{id}", headers = "X-API-Version=2")
  public ResponseEntity<UserResponseV2> findByIdV2(@PathVariable Long id) {
    log.info("[v2] GET /api/users/{}", id);
    return ResponseEntity.ok(userMapperV2.toResponse(userService.findById(id)));
  }

  /**
   * Creates a new user using the v2 request contract.
   *
   * <p>Requires header {@code X-API-Version: 2}.</p>
   * <p>The request body must use the v2 shape: separate
   * {@code firstName} and {@code lastName} fields, plus optional
   * {@code phoneNumber} and {@code department}.</p>
   *
   * @param request the v2 create request body
   * @return {@code 201 Created} with the persisted {@link UserResponseV2},
   *         or {@code 400} if validation fails,
   *         or {@code 409} if the email is already in use
   */
  @ApiV2
  @PostMapping(headers = "X-API-Version=2")
  public ResponseEntity<UserResponseV2> createV2(
      @Valid @RequestBody CreateUserRequestV2 request) {
    log.info("[v2] POST /api/users — email: {}", request.email());
    User saved = userService.create(userMapperV2.toDomain(request));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(userMapperV2.toResponse(saved));
  }

  /**
   * Fully updates an existing user using the v2 request contract.
   *
   * <p>Requires header {@code X-API-Version: 2}.</p>
   * <p>This endpoint does not exist in v1. The {@code id} and
   * {@code createdAt} fields of the record are always preserved
   * regardless of what the request body contains.</p>
   *
   * @param id      the ID of the user to update
   * @param request the v2 update request body
   * @return {@code 200 OK} with the updated {@link UserResponseV2},
   *         or {@code 404} if the ID does not exist,
   *         or {@code 409} if the new email is already taken
   */
  @ApiV2
  @PutMapping(value = "/{id}", headers = "X-API-Version=2")
  public ResponseEntity<UserResponseV2> updateV2(
      @PathVariable Long id,
      @Valid @RequestBody CreateUserRequestV2 request) {
    log.info("[v2] PUT /api/users/{}", id);
    User updated = userService.update(id, userMapperV2.toDomain(request));
    return ResponseEntity.ok(userMapperV2.toResponse(updated));
  }

  /**
   * Deletes a user by ID.
   *
   * <p>Requires header {@code X-API-Version: 2}.</p>
   *
   * @param id the user ID to delete
   * @return {@code 204 No Content} on success,
   *         or {@code 404} if the ID does not exist
   */
  @ApiV2
  @DeleteMapping(value = "/{id}", headers = "X-API-Version=2")
  public ResponseEntity<Void> deleteV2(@PathVariable Long id) {
    log.info("[v2] DELETE /api/users/{}", id);
    userService.delete(id);
    return ResponseEntity.noContent().build();
  }
}