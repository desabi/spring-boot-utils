package com.desabisc.aopa;

import com.desabisc.aopa.service.ExampleService;
import com.desabisc.aopa.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
/**
 * source 1: https://medium.com/@AlexanderObregon/introduction-to-spring-aop-aspect-oriented-programming-in-java-275def80e9ca
 * */
@SpringBootApplication
public class AopaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AopaApplication.class, args);

		ExampleService exampleService = context.getBean(ExampleService.class);
		String greeting = exampleService.hello("John");
		System.out.println(greeting);

		MyService myService = context.getBean(MyService.class);
		myService.performAction();
		System.out.println(myService.getData("36"));
	}

}
