package com.desabisc.aopa.service;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    public String hello(String name) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, " + name + "!";
    }
}
