package com.desabi.guide.api.versioning.header.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc OpenAPI configuration for header-based API versioning.
 *
 * <p>Unlike URI versioning, all endpoints share the same URL path
 * ({@code /api/users}). Swagger UI cannot natively differentiate versions
 * by URL alone, so we use {@link GroupedOpenApi} groups combined with
 * custom operation filters to tag each endpoint with its required header
 * value. This produces two separate documentation pages:</p>
 *
 * <ul>
 *   <li>{@code /swagger-ui.html?group=v1} — deprecated endpoints</li>
 *   <li>{@code /swagger-ui.html?group=v2} — current endpoints</li>
 * </ul>
 *
 * <p>Note: header-based versioning is less discoverable than URI versioning
 * and requires consumers to read this documentation to know which header
 * value to supply.</p>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Top-level OpenAPI metadata shared by all version groups.
     *
     * @return a configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API — Header Versioning")
                        .version("2.0")
                        .description("""
                                Versioned REST API using the X-API-Version request header.
                                All endpoints live at /api/users — the header value selects
                                the version. V1 is deprecated; please migrate to V2.
                                """)
                        .contact(new Contact()
                                .name("Desabi API Team")
                                .email("api@desabi.com")));
    }

    /**
     * OpenAPI group for v1 endpoints.
     *
     * <p>Groups all paths under {@code /api/users} and marks them as
     * belonging to the deprecated v1 contract. Consumers of this group
     * must send {@code X-API-Version: 1} on every request.</p>
     *
     * @return a {@link GroupedOpenApi} scoped to v1
     */
    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1 (deprecated)")
                .pathsToMatch("/api/users/**")
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(
                        com.desabi.guide.api.versioning.header.config.ApiV1.class))
                .build();
    }

    /**
     * OpenAPI group for v2 endpoints.
     *
     * <p>Groups all paths under {@code /api/users} and marks them as
     * belonging to the current v2 contract. Consumers of this group
     * must send {@code X-API-Version: 2} on every request.</p>
     *
     * @return a {@link GroupedOpenApi} scoped to v2
     */
    @Bean
    public GroupedOpenApi v2Api() {
        return GroupedOpenApi.builder()
                .group("v2 (latest)")
                .pathsToMatch("/api/users/**")
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent(
                        com.desabi.guide.api.versioning.header.config.ApiV2.class))
                .build();
    }
}