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

    @Around("execution(* com.desabisc.aopa.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        System.out.println("Method " + joinPoint.getSignature() + " executed in " + (endTime - startTime) + "ms");

        return result;
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

    /**
     * Pointcut expressions start with a Pointcut Designator (PCD),
     * which specifies what methods to be targeted by our Advice.
     * */
    @Pointcut("execution(public void com.desabisc.aopa.service.ShipmentService.shipStuffWithBill())")
    public void logPointcutWithExecution() {}

    /**
     * Now, let’s add Advice matching the above Pointcut
     * */
    @Before("logPointcutWithExecution()")
    public void logMethodCallsWithExecutionAdvice() {
        System.out.println("In Aspect from execution");
    }
}
