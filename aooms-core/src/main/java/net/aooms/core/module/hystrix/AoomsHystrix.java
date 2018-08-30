package net.aooms.core.module.hystrix;

import java.lang.annotation.*;

/**
 * 自定义熔断器注解
 * Created by cccyb on 2018/8/30
 */
@Target({ElementType.METHOD ,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AoomsHystrix {

    public int timeout() default 0; // 超时时间

    public String fallbackMethod(); // 降级处理方法

}
