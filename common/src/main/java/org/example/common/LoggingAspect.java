package org.example.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Aspect
@Component
public class LoggingAspect {

    private final LoggingProducer loggingProducer;

    public LoggingAspect(LoggingProducer loggingProducer) {this.loggingProducer=loggingProducer;}

    //해당 경로의 모든 코드들이 실행되기 직전에 먼저 이 함수를 실행코자 함
    @Before("execution(* org.example.*.adapter.in.web.*.*(..))")
    public void beforeMethodExecution(@NotNull JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        // logging
        loggingProducer.sendMessage("logging","Before executing method: "+methodName);


    }
}
