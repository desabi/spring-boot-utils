package com.desabisc.aopa.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * source: https://reflectoring.io/aop-spring/
 * */
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.desabisc.aopa.service.ExampleService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        System.out.println("Method " + joinPoint.getSignature() + " executed in " + (endTime - startTime) + "ms");

        return result;
    }

    /**
     * Advice: The advice is the action taken at a particular join point.
     * In this case, it's the logBeforeServiceMethods() method annotated with @Before.
     * */
    @Before("execution(* com.desabisc.aopa.service.MyService.*(..))")
    public void logBeforeServiceMethods() {
        System.out.println("Logging before MyService methods");
    }

    /**
     * we used the expression @Pointcut("@annotation(Log)") to describe which potential methods (JoinPoints)
     * are affected by the corresponding Advice method.
     * In this case, we want to add the advice to all methods that are annotated with our @Log annotation.
     * La anotacion debe estar en el mismo paquete
     * */
    @Pointcut("@annotation(MyCustomLog)")
    public void logPointCut(){}

    /**
     *  @Before("logPointcut()") that executes the annotated method logAllMethodCallsAdvice before the execution
     *  of any method annotated with @Log.
     * */
    @Before("logPointCut()")
    public void logAllMethodCallsAdvice() {
        System.out.println("In Aspect");
    }


}
