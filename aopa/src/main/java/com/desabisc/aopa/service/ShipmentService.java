package com.desabisc.aopa.service;

import com.desabisc.aopa.aspect.MyCustomLog;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    @MyCustomLog
    // this here is whats called a join point
    public void shipStuff() {
        System.out.println("In Service (annotation)");
    }

    public void shipStuffWithBill() {
        System.out.println("In service with bill (pointcut)");
    }
}
