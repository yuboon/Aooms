package net.aooms.core.module.dbsharding;

import io.shardingsphere.transaction.constants.SoftTransactionType;

import java.lang.annotation.*;

/**
 * 柔性事务注解
 * Created by 风象南(cheereebo) on 2018/9/7
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SoftTransactional {

    SoftTransactionType value() default SoftTransactionType.BestEffortsDelivery;

}
