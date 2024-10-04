package com.ennaru.monitor.docker.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Aspect
@Component
public class ContainerIdAspect {

    @Around("@annotation(com.ennaru.monitor.docker.annotation.ContainerId)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("logging >>>>");

        // args 추출
        Object[] args = joinPoint.getArgs();
        for(Object arg : args) {
            if(arg instanceof String) {
                if(!Pattern.compile("^[a-zA-Z0-9]*$").matcher((String) arg).find()) {
                    throw new IllegalArgumentException("Invalid container ID: " + (String) arg);
                }
            }
        }

        return joinPoint.proceed();
    }

}
