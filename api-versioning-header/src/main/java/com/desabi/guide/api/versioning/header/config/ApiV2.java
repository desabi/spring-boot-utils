package com.desabi.guide.api.versioning.header.config;

import java.lang.annotation.*;

/**
 * Marker annotation used by {@link OpenApiConfig} to identify methods
 * that belong to the v2 API contract.
 *
 * <p>Apply this annotation to every controller method that is gated
 * behind {@code headers = "X-API-Version=2"} so that the Swagger UI
 * group filter can include it in the v2 documentation page.</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiV2 { }