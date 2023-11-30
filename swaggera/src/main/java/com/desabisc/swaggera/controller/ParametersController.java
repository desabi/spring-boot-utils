package com.desabisc.swaggera.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swaggera")
@Tag(name = "Parametros", description = "Ejemplos de parametros simples")
public class ParametersController {

    @Operation(
            summary = "Age plus ten",
            description = "given an age, adds ten years"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operacion exitosa")
    })
    @GetMapping(value = "/ageplusten", produces = "application/json")
    public ResponseEntity<Integer> age(@RequestParam Integer age) {
        Integer agePlusTen = age + 10;
        return new ResponseEntity<>(agePlusTen, HttpStatus.OK);
    }

    @GetMapping("/add")
    public ResponseEntity<Integer> add(@RequestParam Integer firstNumber, @RequestParam Integer secondNumber) {
        Integer addition = firstNumber + secondNumber;
        return new ResponseEntity<>(addition, HttpStatus.OK);
    }

    @GetMapping("/square/{number}")
    public ResponseEntity<Integer> calculateSquare(@PathVariable("number") Integer number) {
        Integer square = number * number;
        return new ResponseEntity<>(square, HttpStatus.OK);
    }
}
