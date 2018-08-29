package net.aooms.core.module.hystrix;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.javanica.command.CommandExecutor;
import com.netflix.hystrix.contrib.javanica.command.ExecutionType;
import com.netflix.hystrix.contrib.javanica.command.HystrixCommandFactory;
import com.netflix.hystrix.contrib.javanica.command.MetaHolder;
import com.netflix.hystrix.contrib.javanica.utils.AopUtils;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class AoomsHystrixAspect {

    private final Logger log = LoggerFactory.getLogger(AoomsHystrixAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {

    }

    @Around("requestMapping()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        String groupName = pjp.getTarget().getClass().getName();
        log.info("around method {}", pjp.getSignature().getName());

        Signature sig = pjp.getSignature();
        MethodSignature msig = null;

        if (sig instanceof MethodSignature) {
            msig = (MethodSignature) sig;
            Object target = pjp.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            AoomsHystrixCommand command = new AoomsHystrixCommand(groupName){
                @Override
                protected Object run(){
                    try {
                        //return currentMethod.invoke(target,pjp.getArgs());
                        return pjp.proceed();
                    } catch (Throwable e) {
                        throw new RuntimeException(e.getMessage(),e);
                    }
                }

                @Override
                protected Object getFallback() {
                    //System.err.println("getFallback");
                    //return  "1231321";
                    return super.getFallback();
                }
            };
            return command.execute();
        }

        Object result = pjp.proceed();
        System.err.println("pjp.proceed():" + result);
        return result;
    }

    /*@Before("requestMapping()")
    public void before(JoinPoint jp) throws Throwable {
        log.info("before method {}", jp.getSignature().getName());
    }*/

    /*@AfterReturning("requestMapping()")
    public void afterReturning(JoinPoint jp) throws Throwable {
        log.info("afterReturning method {}", jp.getSignature().getName());
    }

    @AfterThrowing("requestMapping()")
    public void afterThrowing(JoinPoint joinPoint) throws Throwable {
        log.info("afterThrowing method is {}",joinPoint.getSignature().getName());
    }

    @After("requestMapping()")
    public void after(JoinPoint joinPoint) {

    }*/
}