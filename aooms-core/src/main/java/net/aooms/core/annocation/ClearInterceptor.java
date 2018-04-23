package net.aooms.core.annocation;

import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.*;

/**
 * 拦截器清理
 * Created by cccyb on 2018-04-23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearInterceptor {

    Class<? extends HandlerInterceptor>[] value() default {};

}