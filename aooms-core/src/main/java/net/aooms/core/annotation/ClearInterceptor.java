package net.aooms.core.annotation;

import net.aooms.core.web.interceptor.AoomsAbstractInterceptor;

import java.lang.annotation.*;

/**
 * 拦截器清理
 * Created by cccyb on 2018-04-23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearInterceptor {

    Class<? extends AoomsAbstractInterceptor>[] value() default {};

}