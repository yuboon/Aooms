package net.aooms.core.module.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * 自定义命令
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public abstract class AoomsHystrixCommand extends HystrixCommand<Object> {

    public AoomsHystrixCommand(String key) {
        super(HystrixCommandGroupKey.Factory.asKey(key));
    }

    public AoomsHystrixCommand(String key, int executionIsolationThreadTimeoutInMilliseconds) {
        super(HystrixCommandGroupKey.Factory.asKey(key), executionIsolationThreadTimeoutInMilliseconds);
    }

    public AoomsHystrixCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    public AoomsHystrixCommand(HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool) {
        super(group, threadPool);
    }

    public AoomsHystrixCommand(HystrixCommandGroupKey group, int executionIsolationThreadTimeoutInMilliseconds) {
        super(group, executionIsolationThreadTimeoutInMilliseconds);
    }

    public AoomsHystrixCommand(HystrixCommandGroupKey group, HystrixThreadPoolKey threadPool, int executionIsolationThreadTimeoutInMilliseconds) {
        super(group, threadPool, executionIsolationThreadTimeoutInMilliseconds);
    }

    public AoomsHystrixCommand(Setter setter) {
        super(setter);
    }

}