package com.example.maidsquizapi.shared.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Pointcut("within(@org.springframework.stereotype.Service *) && execution(public * *(..))")
    public void publicServiceMethods() {
    }

    @Before("publicServiceMethods()")
    public void logMethodStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        logger.info("Starting method: {} with arguments: {}", methodName, arguments);
    }

    @AfterReturning(pointcut = "publicServiceMethods()", returning = "retVal")
    public void logMethodReturn(JoinPoint joinPoint, Object retVal) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} returned: {}", methodName, retVal);
    }

    @AfterThrowing(pointcut = "publicServiceMethods()", throwing = "e")
    public void logMethodException(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Exception in method {}: {}", methodName, e.getMessage());
    }
}
