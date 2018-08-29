package net.aooms.core.module.hystrix;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AoomsHystrixAspect2 {

    private final Logger log = LoggerFactory.getLogger(AoomsHystrixAspect2.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {

    }

    /*@Before("requestMapping()")
    public void before(JoinPoint jp) throws Throwable {
        log.info("before method {}", jp.getSignature().getName());
    }*/

}