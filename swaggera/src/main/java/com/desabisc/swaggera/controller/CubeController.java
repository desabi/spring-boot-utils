package com.desabisc.swaggera.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swaggera")
public class CubeController implements CubeApi {
    @Override
    @GetMapping("/cube/{number}")
    public ResponseEntity<Integer> getCube(@PathVariable("number") Integer number) {
        Integer result = number * number * number;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
