package net.aooms.core;

import net.aooms.core.web.interceptor.AoomsInterceptorRegistryProxy;
import net.aooms.core.web.service.ServiceConfigurations;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * AoomsSetting 框架配置
 * Created by 风象南(yuboon) on 2018-10-08
 */
public abstract class AoomsSetting implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截器注册代理类
        AoomsInterceptorRegistryProxy interceptorRegistryProxy = new AoomsInterceptorRegistryProxy(registry);
        configInterceptor(interceptorRegistryProxy);
    }

    /**
     * Aooms 扩展配置
     *
     * https://blog.csdn.net/superlover_/article/details/80893007  虚拟路径
     */

    // CallServcie 配置
    public abstract void configService(ServiceConfigurations serviceConfigurations);

    // 拦截器配置
    public abstract void configInterceptor(AoomsInterceptorRegistryProxy interceptorRegistryProxy);


}