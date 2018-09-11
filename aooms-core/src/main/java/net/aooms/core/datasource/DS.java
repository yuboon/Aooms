package net.aooms.core.datasource;

import java.lang.annotation.*;


/**
 * 在方法上使用，用于指定使用哪个数据源
 * Created by 风象南(cheereebo) on 2018-08-18
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

    String value() default "";

}