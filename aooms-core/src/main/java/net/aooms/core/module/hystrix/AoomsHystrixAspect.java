package net.aooms.core.module.hystrix;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AoomsHystrixAspect {

    private final Logger log = LoggerFactory.getLogger(AoomsHystrixAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {

    }

    @Around("requestMapping()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        String name = pjp.getSignature().getName();
        try {
            sw.start();
            return pjp.proceed();
        } finally {
            sw.stop();
        }
    }

    @Before("requestMapping()")
    public void before(JoinPoint jp) throws Throwable {
        log.info("before method {}", jp.getSignature().getName());
    }
    @AfterReturning("requestMapping()")
    public void afterReturning(JoinPoint jp) throws Throwable {
        log.info("afterReturning method {}", jp.getSignature().getName());
    }

    @AfterThrowing("requestMapping()")
    public void afterThrowing(JoinPoint joinPoint) throws Throwable {
        log.info("afterThrowing method is {}",joinPoint.getSignature().getName());
    }

    @After("requestMapping()")
    public void after(JoinPoint joinPoint) {

     }
}