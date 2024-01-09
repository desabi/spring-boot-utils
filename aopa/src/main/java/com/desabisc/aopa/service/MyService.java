package com.desabisc.aopa.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    /**
     * Join Point: This refers to the specific points in the application where the advice can be applied.
     * In this example, the join point is specified using the execution pointcut expression that targets all methods
     * in the MyService class (com.example.service.MyService).
     * */
    public void performAction() {
        System.out.println("MyService.performAction()");
        // Method logic goes here
    }

    public String getData(String id) {
        // Method logic goes here
        return "Data for id: " + id;
    }
}
