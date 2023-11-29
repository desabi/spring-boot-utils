package com.desabisc.swaggera.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swaggera")
public class IndexController {

    @GetMapping(path = "")
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("Swagger example a working", HttpStatus.OK);
    }
}
