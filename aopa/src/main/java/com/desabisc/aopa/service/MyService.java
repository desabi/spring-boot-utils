package com.desabisc.aopa.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void performAction() {
        System.out.println("MyService.performAction()");
        // Method logic goes here
    }

    public String getData(String id) {
        // Method logic goes here
        return "Data for id: " + id;
    }
}
