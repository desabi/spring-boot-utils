package com.desabisc.swaggera.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cube", description = "Example: cube of a number")
public interface CubeApi {

    @Operation(
            summary = "get cube",
            description = "calculate the cube of a number"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operacion exitosa")
    })
    ResponseEntity<Integer> getCube(Integer number);
}
